package my.app.sportvideofeedapp.routers

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.app.sportvideofeedapp.core.router.DefaultRouter
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.ui.containerFragment.ContainerFragmentDirections
//import my.app.sportvideofeedapp.ui.containerFragment.feedFragment.FeedFragmentDirections
import javax.inject.Inject

class ContainerRouter @Inject constructor(fragment: Fragment) : DefaultRouter(fragment){
    fun navigateToVideoFragment(feedItem: FeedItem) {
        val action = ContainerFragmentDirections.actionContainerFragmentToVideoFragment(feedItem)
        mFragment.findNavController().navigate(action)
    }
}
