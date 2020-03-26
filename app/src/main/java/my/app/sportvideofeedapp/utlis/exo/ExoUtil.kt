package my.app.sportvideofeedapp.utlis.exo

import android.net.Uri
import android.os.Build
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackPreparer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import my.app.sportvideofeedapp.CURRENT_POSITION_KEY
import my.app.sportvideofeedapp.data.sp.AppPreferenceHelper
import javax.inject.Inject
import javax.inject.Provider

/**
 * A utility class to conduct Exo implementation
 *
 *
 * @author nuhkoca
 *
 * tnx nahkoca :)
 */

// suppressed TooManyFunctions: this is a util class and expected more than 11 methods
@Suppress("TooManyFunctions")
class ExoUtil @Inject constructor(
    private val exoPlayer: Provider<ExoPlayer>,
    private val factory: ProgressiveMediaSource.Factory,
    private val preferenceUtil: AppPreferenceHelper
) : PlaybackPreparer, Player.EventListener {

    private var simpleExoPlayer: ExoPlayer? = null

    private var mPlayerStateListener: PlayerStateListener? = null
    private var mPlayerView: PlayerView? = null

    private var mInitShouldAutoPlay: Boolean = true
    private var initPlayerPosition: Long = 0
    private var mUrl: String? = null

    /**
     * Helps build a [ProgressiveMediaSource.Factory]
     *
     * @param uri represents a url to be played
     * @return an instance of [MediaSource]
     */
    private fun buildMediaSource(uri: Uri?): MediaSource {
        return factory.createMediaSource(uri)
    }

    fun setListener(playerStateListener: PlayerStateListener) {
        this.mPlayerStateListener = playerStateListener
    }

    /**
     * Helps set a [PlayerView] in order to play media
     *
     * @param playerView represents a [PlayerView]
     */
    fun setPlayerView(playerView: PlayerView) {
        this.mPlayerView = playerView
    }

    /**
     * Helps set a URL in order to access the media
     *
     * @param url indicates the media url
     */
    fun setUrl(url: String?) {
        this.mUrl = url
    }

    fun setInitPlayerPosition(playerPosition: Long) {
        this.initPlayerPosition = playerPosition
    }

    fun pauseVideo(){
        mPlayerView?.player?.playWhenReady= false
    }

    fun playVideo(){
        mPlayerView?.player?.playWhenReady= true
    }

    fun seekTo(playerPosition: Long){
        mPlayerView?.player?.seekTo(playerPosition)
    }

    fun getPlayerPosition() = simpleExoPlayer?.currentPosition ?: 0

    fun setPlayerControlDispatcher(controlDispatcher: com.google.android.exoplayer2.ControlDispatcher) {
        if (mPlayerView == null) {
            throw NullPointerException("Set playerView first")
        } else {
            mPlayerView?.setControlDispatcher(controlDispatcher)
        }
    }

    /**
     * Initializes the [ExoUtil.exoPlayer]
     */
    private fun initializePlayer() {
        simpleExoPlayer = exoPlayer.get()
        simpleExoPlayer?.seekTo(initPlayerPosition)
        mPlayerView?.apply {
            player = simpleExoPlayer
            setPlaybackPreparer(this@ExoUtil)
        }
        simpleExoPlayer?.apply {
            addListener(this@ExoUtil)
            playWhenReady = mInitShouldAutoPlay
        }

        mUrl?.let { url ->
            simpleExoPlayer?.prepare(buildMediaSource(Uri.parse(url)))
        }
        simpleExoPlayer?.seekTo(initPlayerPosition)
    }

    /**
     * Releases the [ExoUtil.exoPlayer]
     */
    private fun releasePlayer() {
        simpleExoPlayer?.let { exoPlayer ->
            exoPlayer.stop()
            exoPlayer.release()
            exoPlayer.removeListener(this)
            mInitShouldAutoPlay = exoPlayer.playWhenReady
            preferenceUtil.putLongData(CURRENT_POSITION_KEY, exoPlayer.currentPosition)
            simpleExoPlayer = null
        }
    }

    /**
     * Gets called when Exo prepares its playback
     */
    override fun preparePlayback() {
        initializePlayer()
    }

    /**
     * Gets called when there is an error to play video
     *
     * @param error represents an error
     */
    override fun onPlayerError(error: ExoPlaybackException) {
        mPlayerStateListener?.onPlayerError(error)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        mPlayerStateListener?.onPlayerStateChanged(playbackState)
    }

    /**
     * Clears references
     */
    fun onStart() {
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            initializePlayer()
            mPlayerView?.onResume()
        }
    }

    /**
     * Regains references
     */
    fun onResume() {
        if (Util.SDK_INT <= Build.VERSION_CODES.M || mPlayerView == null) {
            initializePlayer()
            mPlayerView?.onResume()
        }
    }

    /**
     * Clears references
     */
    fun onPause() {
        if (Util.SDK_INT <= Build.VERSION_CODES.M) {
            mPlayerView?.onPause()
            releasePlayer()
        }
    }

    /**
     * Regains references
     */
    fun onStop() {
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            mPlayerView?.onPause()
            releasePlayer()
        }
    }

    /**
     * A listener that handles media playback
     */
    interface PlayerStateListener {

        /**
         * Gets called playback state has been changed
         *
         * @param playbackState indicates the playback state
         */
        fun onPlayerStateChanged(playbackState: Int)

        /**
         * Gets called when there is an error to play media
         */
        fun onPlayerError(error: ExoPlaybackException)
    }
}
