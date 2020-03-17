package my.app.sportvideofeedapp.core.views

import my.app.sportvideofeedapp.routers.NavigationPlaces

interface NavigationView<NP : NavigationPlaces> {
    fun navigate(navigateTo: NP)
}
