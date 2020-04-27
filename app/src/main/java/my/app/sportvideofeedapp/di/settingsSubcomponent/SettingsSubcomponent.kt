package my.app.sportvideofeedapp.di.settingsSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.sportvideofeedapp.ui.containerFragment.settingsFragment.SettingsFragment

@Subcomponent
interface SettingsSubcomponent {

    fun inject(settingsFragment: SettingsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): SettingsSubcomponent
    }
}
