package my.app.sportvideofeedapp.di

import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.utlis.converters.SecondsToMinutesConverter
import my.app.sportvideofeedapp.utlis.converters.StringToDateConverter
import javax.inject.Singleton

@Module
interface ConvertersModule {
    companion object {
        @Provides
        @Singleton
        fun provideSecondsToMinutesConverter(): SecondsToMinutesConverter {
            return SecondsToMinutesConverter
        }

        @Provides
        @Singleton
        fun provideStringToDateConverter(): StringToDateConverter {
            return StringToDateConverter
        }
    }
}
