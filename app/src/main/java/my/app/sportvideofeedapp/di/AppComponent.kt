package my.app.sportvideofeedapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.di.feedSubcomponent.FeedSubcomponent
import my.app.sportvideofeedapp.di.feedSubcomponent.FeedSubcomponentFactory
import my.app.sportvideofeedapp.di.qualifiers.AppContext
import my.app.sportvideofeedapp.di.videoSubcomponent.VideoSubcomponent
import my.app.sportvideofeedapp.di.videoSubcomponent.VideoSubcomponentFactory
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
        NetworkSourceModule::class,
        SchedulerModule::class,
        FeedSubcomponentFactory::class,
        VideoSubcomponentFactory::class,
        ViewModelFactoryModule::class,
        ConvertersModule::class,
        MappersModule::class,
        ExoModule::class
    ]
)
interface AppComponent {

    fun inject(app: BaseApplication)
    fun getFeedSubcomponentFactory(): FeedSubcomponent.Factory
    fun getVideoSubcomponentFactory(): VideoSubcomponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @AppContext appContext: Context): AppComponent
    }
}
