package my.app.sportvideofeedapp.viewmodels

import my.app.sportvideofeedapp.core.viewModel.NetworkViewModel
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces
import javax.inject.Inject

class FeedViewModel @Inject constructor() :
    NetworkViewModel<PlaceHolderNavigationPlaces>()
