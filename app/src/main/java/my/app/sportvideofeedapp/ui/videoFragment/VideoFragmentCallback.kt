package my.app.sportvideofeedapp.ui.videoFragment

interface VideoFragmentCallback {
    fun playPressed()
    fun pausePressed()
    fun seekTo(positionMs: Long)
}
