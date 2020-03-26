package my.app.sportvideofeedapp.utlis.exo

import com.google.android.exoplayer2.DefaultControlDispatcher
import com.google.android.exoplayer2.Player
import my.app.sportvideofeedapp.ui.videoFragment.VideoFragmentCallback

class CustomPlayerControlDispatcher(private val mCallback: VideoFragmentCallback) :
    DefaultControlDispatcher() {
    override fun dispatchSetPlayWhenReady(player: Player, playWhenReady: Boolean): Boolean {
        if (playWhenReady) {
            mCallback.playPressed()
        } else {
            mCallback.pausePressed()
        }
        return true
    }

    override fun dispatchSeekTo(player: Player, windowIndex: Int, positionMs: Long): Boolean {
        mCallback.seekTo(positionMs)
        return true
    }
}
