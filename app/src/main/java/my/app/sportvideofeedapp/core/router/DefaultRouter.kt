package my.app.sportvideofeedapp.core.router

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class DefaultRouter(private val fragment: Fragment) : Router {

    override fun navigateBack() {
        if (fragment.findNavController().navigateUp()) {
            exitApp()
        }
    }

    override fun exitApp() {
        fragment.activity!!.finishAndRemoveTask()
    }
}
