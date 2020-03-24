package my.app.sportvideofeedapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.sportvideofeedapp.di.key.ViewModelKey
import my.app.sportvideofeedapp.viewmodels.DaggerViewModelFactory
import my.app.sportvideofeedapp.viewmodels.FeedViewModel
import my.app.sportvideofeedapp.viewmodels.VideoViewModel

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun provideFeedViewModel(feedViewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    fun provideVideoViewModel(videoViewModel: VideoViewModel): ViewModel
}
