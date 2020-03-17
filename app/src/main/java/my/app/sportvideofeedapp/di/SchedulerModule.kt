package my.app.sportvideofeedapp.di

import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.utlis.scheduler.AppSchedulerProvider
import javax.inject.Singleton

@Module
interface SchedulerModule {

    companion object {

        @Singleton
        @Provides
        fun provideAppSchedulerProvider(): AppSchedulerProvider {
            return AppSchedulerProvider
        }
    }
}
