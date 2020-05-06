package my.app.sportvideofeedapp.routers

import androidx.fragment.app.Fragment
import my.app.sportvideofeedapp.core.router.DefaultRouter
import javax.inject.Inject

class ContainerRouter @Inject constructor(fragment: Fragment) : DefaultRouter(fragment)
