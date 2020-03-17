package my.app.sportvideofeedapp.di.placeHolderSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.sportvideofeedapp.di.scope.PerFragment
import my.app.sportvideofeedapp.ui.PlaceHolderFragment

@PerFragment
@Subcomponent(
    modules = [
        PlaceHolderModule::class
    ]
)
interface PlaceHolderComponent {
    fun inject(placeHolderFragment: PlaceHolderFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): PlaceHolderComponent
    }
}
