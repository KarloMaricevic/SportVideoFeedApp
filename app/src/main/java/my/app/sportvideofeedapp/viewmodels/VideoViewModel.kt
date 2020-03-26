package my.app.sportvideofeedapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel
import my.app.sportvideofeedapp.data.entities.FeedItem
import javax.inject.Inject

class VideoViewModel @Inject constructor() : BaseViewModel() {

    enum class PlayerState {
        STATE_IDLE,
        STATE_BUFFERING,
        STATE_READY,
        STATE_ENDED
    }

    // doesn't make sense that this data would be MutableLiveData  [might be wrong]

    private lateinit var feedItem: FeedItem

    // should I save this in bundle and not here?

    private var savedPlayerPosition: Long = 0

    private val playerState = MutableLiveData<PlayerState>()

    private val playWhenReady = MutableLiveData(true)

    private val playerError = MutableLiveData<ExoPlaybackException>()

    private val seekToEvent = PublishRelay.create<Long>()

    fun getFeedItem() = feedItem

    fun getSavedPlayerPosition() = savedPlayerPosition

    fun setSavedPlayerPosition(playerPosition: Long) {
        savedPlayerPosition = playerPosition
    }

    fun getEvents() = seekToEvent as Observable<Long>

    fun getPlayerState() = playerState as LiveData<PlayerState>

    fun getPlayWhenReady() = playWhenReady as LiveData<Boolean>

    fun setFeedItem(feedItem: FeedItem) {
        this.feedItem = feedItem
    }

    fun setState(playerState: PlayerState) {
        this.playerState.value = playerState
    }

    fun setError(error : ExoPlaybackException) {
        playerError.value = error
    }

    fun getError() = playerError as LiveData<ExoPlaybackException>

    fun pauseVideo() {
        playWhenReady.value = false
    }

    fun playVideo() {
        playWhenReady.value = true
    }

    fun seekTo(positionInMs: Long) {
        seekToEvent.accept(positionInMs)
    }
}
