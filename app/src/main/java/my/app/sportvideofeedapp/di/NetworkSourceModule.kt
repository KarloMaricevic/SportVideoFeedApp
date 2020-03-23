package my.app.sportvideofeedapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.BASE_URL
import my.app.sportvideofeedapp.CONNECT_TIMEOUT_SECONDS
import my.app.sportvideofeedapp.READ_TIMEOUT_SECONDS
import my.app.sportvideofeedapp.WRITE_TIMEOUT_SECONDS
import my.app.sportvideofeedapp.data.network.ApiHelper
import my.app.sportvideofeedapp.data.network.ApiHelperImplementation
import my.app.sportvideofeedapp.data.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
interface NetworkSourceModule {

    companion object {

        @Provides
        @Singleton
        fun provideGson(): Gson {
            val gsonBuilder = GsonBuilder()
            return gsonBuilder.create()
        }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        @Provides
        fun provideClient(logInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient
                .Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

    @Binds
    @Singleton
    fun providesApiHelper(apiHelperImplementation: ApiHelperImplementation): ApiHelper
}
