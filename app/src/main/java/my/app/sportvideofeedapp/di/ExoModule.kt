package my.app.sportvideofeedapp.di

import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.EXO_PLAYER_USER_AGENT
import my.app.sportvideofeedapp.EXO_PLAYER_VIDEO_CACHE_DURATION
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.di.qualifiers.AppContext
import my.app.sportvideofeedapp.utlis.helper.DefaultCacheDataSourceFactory
import java.io.File
import javax.inject.Singleton

@Module
interface ExoModule {

    @Suppress("TooManyFunctions")
    companion object {
        @Singleton
        @Provides
        fun provideUserAgent(@AppContext appContext: Context): String {
            return Util.getUserAgent(appContext, EXO_PLAYER_USER_AGENT)
        }

        @Singleton
        @Provides
        fun provideDefaultBandwidthsMeter(@AppContext appContext: Context): DefaultBandwidthMeter {
            return DefaultBandwidthMeter.Builder(appContext).build()
        }

        @Singleton
        @Provides
        fun provideHttpDataSourceFactory(
            userAgent: String,
            bandwidthMeter: DefaultBandwidthMeter
        ): HttpDataSource.Factory {
            return DefaultHttpDataSourceFactory(userAgent, bandwidthMeter)
        }

        @Singleton
        @Provides
        fun provideLoadControl(): LoadControl {
            return DefaultLoadControl()
        }

        @Singleton
        @Provides
        fun provideDefaultRenderersFactory(@AppContext appContext: Context): RenderersFactory {
            return DefaultRenderersFactory(appContext)
        }

        @Provides
        fun provideExoPlayer(
            @AppContext appContext: Context,
            loadControl: LoadControl,
            renderersFactory: RenderersFactory
        ): ExoPlayer {
            val exoPlayer = SimpleExoPlayer
                .Builder(appContext, renderersFactory)
                .setLoadControl(loadControl)
                .build()
            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

            return exoPlayer
        }

        @Singleton
        @Provides
        fun provideLeastRecentlyUsedCacheEvictor(): LeastRecentlyUsedCacheEvictor {
            return LeastRecentlyUsedCacheEvictor(EXO_PLAYER_VIDEO_CACHE_DURATION.toLong())
        }

        @Singleton
        @Provides
        fun provideDatabaseProvider(@AppContext appContext: Context): DatabaseProvider {
            return ExoDatabaseProvider(appContext)
        }

        @Singleton
        @Provides
        fun provideCache(
            @AppContext appContext: Context,
            evictor: LeastRecentlyUsedCacheEvictor,
            databaseProvider: DatabaseProvider
        ): Cache {
            return SimpleCache(
                File(appContext.cacheDir, appContext.getString(R.string.cache_type)),
                evictor,
                databaseProvider
            )
        }

        @Singleton
        @Provides
        fun provideCacheDataSink(simpleCache: Cache): CacheDataSink {
            return CacheDataSink(simpleCache, EXO_PLAYER_VIDEO_CACHE_DURATION.toLong())
        }

        @Singleton
        @Provides
        fun provideDefaultDataSourceFactory(
            @AppContext context: Context,
            bandwidthMeter: DefaultBandwidthMeter,
            factory: HttpDataSource.Factory
        ): DefaultDataSourceFactory {
            return DefaultDataSourceFactory(context, bandwidthMeter, factory)
        }

        @Singleton
        @Provides
        fun provideCacheDataSource(
            simpleCache: Cache,
            factory: DefaultDataSourceFactory,
            cacheDataSink: CacheDataSink
        ): CacheDataSource {
            return CacheDataSource(
                simpleCache,
                factory.createDataSource(),
                FileDataSource(),
                cacheDataSink,
                CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                null
            )
        }

        @Singleton
        @Provides
        fun provideCacheDataSourceFactory(cacheDataSource: CacheDataSource): DataSource.Factory {
            return DefaultCacheDataSourceFactory(cacheDataSource)
        }

        @Singleton
        @Provides
        fun provideProgressiveMediaSourceFactory(factory: DataSource.Factory): ProgressiveMediaSource.Factory {
            return ProgressiveMediaSource.Factory(factory)
        }
    }
}
