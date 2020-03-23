package my.app.sportvideofeedapp.di

import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.utlis.scheduler.AppSchedulerProvider
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider
import javax.inject.Singleton

@Module
interface SchedulerModule {

    companion object {

        @Singleton
        @Provides
        fun provideAppSchedulerProvider(): SchedulerProvider {
            return AppSchedulerProvider
        }
    }
}
