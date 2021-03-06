package my.app.sportvideofeedapp.di.feedSubcomponent

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.sportvideofeedapp.ui.containerFragment.feedFragment.FeedFragmentCallback
import my.app.sportvideofeedapp.di.qualifiers.FragmentContext
import my.app.sportvideofeedapp.di.scope.PerFragment
import my.app.sportvideofeedapp.ui.containerFragment.feedFragment.FeedFragment

@PerFragment
@Subcomponent(
    modules = [
        FeedModule::class
    ]
)
interface FeedSubcomponent {
    fun inject(feedFragment: FeedFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment, @BindsInstance feedFragmentCallback: FeedFragmentCallback, @BindsInstance @FragmentContext context: Context): FeedSubcomponent
    }
}
