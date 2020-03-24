package my.app.sportvideofeedapp.ui.videoFragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import my.app.sportvideofeedapp.utlis.exo.ExoUtil

class ExoUtilHandler(private val exoUtil: ExoUtil) : LifecycleObserver {

    /**
     * Regains references
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        exoUtil.onStart()
    }

    /**
     * Regains references
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        exoUtil.onResume()
    }

    /**
     * Clears references
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        exoUtil.onPause()
    }

    /**
     * Clears references
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        exoUtil.onStop()
    }
}
