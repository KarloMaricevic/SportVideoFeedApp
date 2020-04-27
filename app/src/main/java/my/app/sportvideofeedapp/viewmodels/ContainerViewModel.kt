package my.app.sportvideofeedapp.viewmodels

import my.app.sportvideofeedapp.core.viewModel.BaseViewModel
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.routers.ContainerNavigationPlaces
import javax.inject.Inject

class ContainerViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToVideoFragment(feedItem: FeedItem) {
        navigateTo.accept(ContainerNavigationPlaces.NavigateToVideoFragment(feedItem))
    }
}
