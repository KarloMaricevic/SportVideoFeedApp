package my.app.sportvideofeedapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.sportvideofeedapp.di.key.ViewModelKey
import my.app.sportvideofeedapp.viewmodels.DaggerViewModelFactory
import my.app.sportvideofeedapp.viewmodels.FeedViewModel

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun provideFeedViewModel(feedViewModel: FeedViewModel): ViewModel
}
