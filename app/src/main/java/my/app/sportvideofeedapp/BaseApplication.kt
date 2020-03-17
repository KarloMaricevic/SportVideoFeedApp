package my.app.sportvideofeedapp

import android.app.Application
import my.app.sportvideofeedapp.di.AppComponent
import my.app.sportvideofeedapp.di.DaggerAppComponent

class BaseApplication : Application() {
    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.factory().create(this)
    }

    fun getAppComponent() = mAppComponent
}
