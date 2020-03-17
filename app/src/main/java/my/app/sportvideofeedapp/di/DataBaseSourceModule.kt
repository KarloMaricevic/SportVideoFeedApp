package my.app.sportvideofeedapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.data.db.AppDatabase
import my.app.sportvideofeedapp.data.db.dao.EntityPlaceholderDao
import javax.inject.Singleton

@Module
interface DataBaseSourceModule {

    companion object {

        @Provides
        @Singleton
        fun provideAppDatabase(context: Context): AppDatabase {
            return AppDatabase.getDatabaseInstance(context)
        }

        @Provides
        @Singleton
        fun providesPlaceHolderDao(appDatabase: AppDatabase): EntityPlaceholderDao {
            return appDatabase.getPlaceholderDao()
        }
    }
}
