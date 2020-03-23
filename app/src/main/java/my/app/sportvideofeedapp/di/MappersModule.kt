package my.app.sportvideofeedapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.sportvideofeedapp.data.entities.Athlete
import my.app.sportvideofeedapp.data.entities.Author
import my.app.sportvideofeedapp.data.entities.Country
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.entities.SportGroup
import my.app.sportvideofeedapp.data.entities.Video
import my.app.sportvideofeedapp.data.network.pojo.AthleteResponse
import my.app.sportvideofeedapp.data.network.pojo.AuthorResponse
import my.app.sportvideofeedapp.data.network.pojo.CountryResponse
import my.app.sportvideofeedapp.data.network.pojo.FeedItemResponse
import my.app.sportvideofeedapp.data.network.pojo.SportResponse
import my.app.sportvideofeedapp.data.network.pojo.SportsGroupResponse
import my.app.sportvideofeedapp.data.network.pojo.VideoResponse
import my.app.sportvideofeedapp.utlis.mappers.AthleteMapper
import my.app.sportvideofeedapp.utlis.mappers.AuthorMapper
import my.app.sportvideofeedapp.utlis.mappers.BasicListMapper
import my.app.sportvideofeedapp.utlis.mappers.CountryMapper
import my.app.sportvideofeedapp.utlis.mappers.FeedItemMapper
import my.app.sportvideofeedapp.utlis.mappers.SportGroupMapper
import my.app.sportvideofeedapp.utlis.mappers.SportMapper
import my.app.sportvideofeedapp.utlis.mappers.VideoMapper
import javax.inject.Singleton

@Module
interface MappersModule {
    companion object {
        @Provides
        @Singleton
        fun providesAuthorMapper(): BasicListMapper<AuthorResponse, Author> {
            return AuthorMapper
        }

        @Provides
        @Singleton
        fun providesCountyMapper(): BasicListMapper<CountryResponse, Country> {
            return CountryMapper
        }

        @Provides
        @Singleton
        fun providesSpotGroupMapper(): BasicListMapper<SportsGroupResponse, SportGroup> {
            return SportGroupMapper
        }

        @Provides
        @Singleton
        fun providesSportMapper(): BasicListMapper<SportResponse, Sport> {
            return SportMapper
        }
    }

    @Binds
    @Singleton
    fun providesVideoMapper(videoMapper: VideoMapper): BasicListMapper<VideoResponse, Video>

    @Binds
    @Singleton
    fun providesAthleteMapper(athleteMapper: AthleteMapper): BasicListMapper<AthleteResponse, Athlete>

    @Binds
    @Singleton
    fun bindsFeedItemMapper(feedItemMapper: FeedItemMapper): BasicListMapper<FeedItemResponse, FeedItem>
}
