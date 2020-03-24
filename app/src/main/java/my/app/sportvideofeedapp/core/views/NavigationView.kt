package my.app.sportvideofeedapp.core.views

import my.app.sportvideofeedapp.routers.NavigationPlaces

interface NavigationView {
    fun navigate(navigateTo: NavigationPlaces)
}
