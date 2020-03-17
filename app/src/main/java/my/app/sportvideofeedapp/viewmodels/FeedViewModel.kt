package my.app.sportvideofeedapp.viewmodels.placeholderViewmodel

import my.app.sportvideofeedapp.core.viewModel.NetworkViewModel
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces
import javax.inject.Inject

class PlaceHolderViewModel @Inject constructor() :
    NetworkViewModel<PlaceHolderNavigationPlaces>()
