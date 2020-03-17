package my.app.sportvideofeedapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.app.sportvideofeedapp.data.db.dao.EntityPlaceholderDao
import my.app.sportvideofeedapp.data.db.model.EntityPlaceHolder

@Database(entities = [EntityPlaceHolder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "database"
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance as AppDatabase
        }
    }

    fun destroyInstance() {
        instance = null
    }

    abstract fun getPlaceholderDao(): EntityPlaceholderDao
}
