package my.app.sportvideofeedapp.routers

sealed class NavigationPlaces {
     object NavigateBack : NavigationPlaces()
     object ExitApp : NavigationPlaces()
}

// trying to find better solution but for now (all sealed classes must be in the same file)

sealed class PlaceHolderNavigationPlaces : NavigationPlaces()
