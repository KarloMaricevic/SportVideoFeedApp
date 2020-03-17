package my.app.sportvideofeedapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.di.placeHolderSubcomponent.PlaceHolderComponent
import my.app.sportvideofeedapp.di.placeHolderSubcomponent.PlaceHolderSubcomponentFactory
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
    DataBaseSourceModule::class,
    NetworkSourceModule::class,
    SharedPreferenceSourceModule::class,
    SchedulerModule::class,
    PlaceHolderSubcomponentFactory::class,
    ViewModelFactoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: BaseApplication)
    fun getPlaceHolderSubcomponentFactory(): PlaceHolderComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}
