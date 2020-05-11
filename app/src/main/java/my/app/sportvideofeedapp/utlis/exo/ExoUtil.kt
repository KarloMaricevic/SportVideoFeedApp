package my.app.sportvideofeedapp.utlis.exo

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoUtil @Inject constructor(
    val mExoPlayer: ExoPlayer,
    private val mFactory: ProgressiveMediaSource.Factory
) {

    var playingURL: String? = null
    var initPlayerPosition = 0L

    fun setUpExoPlayer(videoUrl: String): ExoPlayer {
        val mediaSource = mFactory.createMediaSource(Uri.parse(videoUrl))
        playingURL = videoUrl
        mExoPlayer.prepare(mediaSource)
        mExoPlayer.seekTo(initPlayerPosition)
        return mExoPlayer
    }

    fun stopPlayer() {
        mExoPlayer.stop()
    }
}
