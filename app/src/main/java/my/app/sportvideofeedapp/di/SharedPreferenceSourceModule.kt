package my.app.sportvideofeedapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.data.sp.AppPreferenceHelper
import javax.inject.Singleton

@Module
interface SharedPreferenceSourceModule {

    companion object {

        @Singleton
        @Provides
        fun provideSharedPreferences(appContext: Context): AppPreferenceHelper {
            return AppPreferenceHelper(appContext)
        }
    }
}
