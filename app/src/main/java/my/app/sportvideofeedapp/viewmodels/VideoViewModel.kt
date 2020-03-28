package my.app.sportvideofeedapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlaybackException
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

    // doesn't make sense that this property would be MutableLiveData  [might be wrong]

    private lateinit var mFeedItem: FeedItem

    private val mIsShowMorePressed = MutableLiveData<Boolean>()

    // should I save this in bundle and not here?

    private var mSavedPlayerPosition: Long = 0

    private val mPlayerState = MutableLiveData<PlayerState>()

    private val mPlayWhenReady = MutableLiveData(true)

    private val mPlayerError = MutableLiveData<ExoPlaybackException>()

    private val mSeekToEvent = PublishRelay.create<Long>()

    // region getter & setters

    fun getSavedPlayerPosition() = mSavedPlayerPosition

    fun setSavedPlayerPosition(playerPosition: Long) {
        mSavedPlayerPosition = playerPosition
    }

    fun getEvents() = mSeekToEvent as Observable<Long>

    fun getPlayerState() = mPlayerState as LiveData<PlayerState>

    fun getPlayWhenReady() = mPlayWhenReady as LiveData<Boolean>

    fun setState(playerState: PlayerState) {
        this.mPlayerState.value = playerState
    }

    fun setError(error: ExoPlaybackException) {
        mPlayerError.value = error
    }

    fun getError() = mPlayerError as LiveData<ExoPlaybackException>

    fun pauseVideo() {
        mPlayWhenReady.value = false
    }

    fun playVideo() {
        mPlayWhenReady.value = true
    }

    fun seekTo(positionInMs: Long) {
        mSeekToEvent.accept(positionInMs)
    }

    fun getFeedItem() = mFeedItem

    fun setFeedItem(feedItem: FeedItem) {
        mFeedItem = feedItem
    }

    fun getIsShowMorePressed() = mIsShowMorePressed
    //endregion
}
