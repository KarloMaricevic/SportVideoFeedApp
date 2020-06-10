package my.app.sportvideofeedapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.sportvideofeedapp.di.key.ViewModelKey
import my.app.sportvideofeedapp.ui.containerFragment.ContainerViewModel
import my.app.sportvideofeedapp.ui.containerFragment.feedFragment.FeedViewModel
import my.app.sportvideofeedapp.ui.containerFragment.settingsFragment.SettingsViewModel
import my.app.sportvideofeedapp.ui.containerFragment.SharedContainerViewModel

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ContainerViewModel::class)
    fun provideContainerViewModel(containerViewModel: ContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun provideSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun provideFeedViewModel(feedViewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedContainerViewModel::class)
    fun provideSharedContainerViewModel(sharedContainerViewModel: SharedContainerViewModel): ViewModel
}
