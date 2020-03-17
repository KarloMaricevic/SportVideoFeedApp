package my.app.sportvideofeedapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.di.feedSubcomponent.FeedSubcomponent
import my.app.sportvideofeedapp.di.feedSubcomponent.FeedSubcomponentFactory
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
    NetworkSourceModule::class,
    SchedulerModule::class,
    FeedSubcomponentFactory::class,
    ViewModelFactoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: BaseApplication)
    fun getFeedSubcomponentFactory(): FeedSubcomponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}
