package my.app.sportvideofeedapp.di.containerSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.sportvideofeedapp.ui.containerFragment.ContainerFragment

@Subcomponent
interface ContainerSubcomponent {

    fun inject(containerFragment: ContainerFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ContainerSubcomponent
    }
}
