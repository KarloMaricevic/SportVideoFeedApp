package my.app.sportvideofeedapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.sportvideofeedapp.di.key.ViewModelKey
import my.app.sportvideofeedapp.viewmodels.ContainerViewModel
import my.app.sportvideofeedapp.viewmodels.DaggerViewModelFactory
import my.app.sportvideofeedapp.viewmodels.FeedViewModel
import my.app.sportvideofeedapp.viewmodels.SettingsViewModel
import my.app.sportvideofeedapp.viewmodels.SharedContainerViewModel
import my.app.sportvideofeedapp.viewmodels.VideoViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    fun provideVideoViewModel(videoViewModel: VideoViewModel): ViewModel
}
