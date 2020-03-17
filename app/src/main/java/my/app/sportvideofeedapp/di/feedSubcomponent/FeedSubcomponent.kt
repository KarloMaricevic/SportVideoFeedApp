package my.app.sportvideofeedapp.di.feedSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.sportvideofeedapp.di.scope.PerFragment
import my.app.sportvideofeedapp.ui.FeedFragment

@PerFragment
@Subcomponent(
    modules = [
        FeedModule::class
    ]
)
interface FeedComponent {
    fun inject(feedFragment: FeedFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): FeedComponent
    }
}
