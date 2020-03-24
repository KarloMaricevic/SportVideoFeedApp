package my.app.sportvideofeedapp.di.videoSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.sportvideofeedapp.di.scope.PerFragment
import my.app.sportvideofeedapp.ui.videoFragment.VideoFragment

@PerFragment
@Subcomponent
interface VideoSubcomponent {
    fun inject(videoFragment: VideoFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): VideoSubcomponent
    }
}
