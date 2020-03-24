package my.app.sportvideofeedapp.routers

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.app.sportvideofeedapp.core.router.DefaultRouter
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.ui.feedFragment.FeedFragmentDirections
import javax.inject.Inject

class FeedRouter @Inject constructor(fragment: Fragment) : DefaultRouter(fragment){
    fun navigateToVideoFragment(feedItem: FeedItem) {
        val action = FeedFragmentDirections.actionPlaceHolderFragmentToVideoFragment(feedItem)
        mFragment.findNavController().navigate(action)
    }
}
