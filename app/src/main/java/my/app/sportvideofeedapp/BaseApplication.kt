package my.app.sportvideofeedapp

import android.app.Application
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import androidx.appcompat.app.AppCompatDelegate
import my.app.sportvideofeedapp.di.AppComponent
import my.app.sportvideofeedapp.di.DaggerAppComponent

class BaseApplication : Application() {
    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.factory().create(this)
        setStyle()
    }

    fun getAppComponent() = mAppComponent

    private fun setStyle() {
        if (Build.VERSION.SDK_INT < Q) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
