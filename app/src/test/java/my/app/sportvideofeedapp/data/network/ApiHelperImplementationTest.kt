package my.app.sportvideofeedapp.data.network

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
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
import my.app.sportvideofeedapp.utlis.mappers.BasicListMapper
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.text.SimpleDateFormat

@ExtendWith(MockKExtension::class)
class ApiHelperImplementationTest(
    @MockK private val mApiService: ApiService,
    @MockK private val mSchedulerProvider: SchedulerProvider,
    @MockK private val mFeedItemMapper: BasicListMapper<FeedItemResponse, FeedItem>,
    @MockK private val mSportItemMapper: BasicListMapper<SportResponse, Sport>
) {

    private val mApiHelperImplementation =
        ApiHelperImplementation(mApiService, mSchedulerProvider, mFeedItemMapper, mSportItemMapper)

    @BeforeEach
    fun mockSchedulerProvider() {
        every { mSchedulerProvider.io() } returns Schedulers.trampoline()
    }

    @Nested
    inner class GetFeedPageTest {

        private val somePage = 1
        private val slugForPopulatedResponse = "SlugForPopulatedResponse"
        private val slugForEmptyResponse = "SlugForEmptyResponse"
        private val slugForExceptionResponse = "SlugForExceptionResponse"

        private val someVideoLengthInSeconds = "125"
        private val dateString = "2019-08-22T12:22:22+00:00"
        private val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(dateString)!!
        private val someAge = 25

        private val populatedFeedItemResponseList = listOf(
            FeedItemResponse(
                "id1",
                dateString,
                "1 day ago",
                "description1",
                AuthorResponse(1, "AuthorName1"),
                SportsGroupResponse(1, "SportGroupName1"),
                VideoResponse(
                    "handler1",
                    "https//:VideoURL",
                    "https//:ThumbnailURL1",
                    "type1",
                    someVideoLengthInSeconds
                ),
                AthleteResponse(
                    1,
                    someAge,
                    "AthleteName1",
                    "htpps://AvatarURL1",
                    "Club1",
                    false,
                    CountryResponse(1, "CountryName1", "Slug1", "https://IconURL1"),
                    SportResponse(1, "sportSlug1", "SportName1", "https://IconURL1")
                ),
                false,
                "WhoWatched1"
            ),

            FeedItemResponse(
                "id2",
                dateString,
                "1 day ago",
                "description2",
                AuthorResponse(2, "AuthorName2"),
                SportsGroupResponse(2, "SportGroupName2"),
                VideoResponse(
                    "handler2",
                    "https//:VideoURL",
                    "https//:ThumbnailURL1",
                    "type2",
                    someVideoLengthInSeconds
                ),
                AthleteResponse(
                    2,
                    someAge,
                    "AthleteName2",
                    "htpps://AvatarURL1",
                    "Club2",
                    false,
                    CountryResponse(1, "CountryName2", "Slug2", "https://IconURL2"),
                    SportResponse(2, "sportSlug2", "SportName2", "https://IconURL2")
                ),
                false,
                "WhoWatched2"
            )
        )
        private val populatedFeedItemList = listOf(
            FeedItem(
                "id1",
                date,
                "1 day ago",
                "description1",
                Author(1, "AuthorName1"),
                SportGroup(1, "SportGroupName1"),
                Video(
                    "handler1",
                    "https//:VideoURL",
                    "https//:ThumbnailURL1",
                    "type1",
                    someVideoLengthInSeconds
                ),
                Athlete(
                    1,
                    someAge,
                    "AthleteName1",
                    "htpps://AvatarURL1",
                    "Club1",
                    false,
                    Country(1, "CountryName1", "Slug1", "https://IconURL1"),
                    Sport(1, "sportSlug1", "SportName1", "https://IconURL1")
                ),
                false,
                "WhoWatched1"
            ),

            FeedItem(
                "id2",
                date,
                "1 day ago",
                "description2",
                Author(2, "AuthorName2"),
                SportGroup(2, "SportGroupName2"),
                Video(
                    "handler2",
                    "https//:VideoURL",
                    "https//:ThumbnailURL1",
                    "type2",
                    someVideoLengthInSeconds
                ),
                Athlete(
                    2,
                    someAge,
                    "AthleteName2",
                    "htpps://AvatarURL1",
                    "Club2",
                    false,
                    Country(1, "CountryName2", "Slug2", "https://IconURL2"),
                    Sport(2, "sportSlug2", "SportName2", "https://IconURL2")
                ),
                false,
                "WhoWatched2"
            )
        )

        private val emptyFeedItemResponseList = listOf<FeedItemResponse>()
        private val emptyFeedItemList = listOf<FeedItem>()

        @BeforeEach
        fun mockApiService() {
            every {
                mApiService.getFeedPage(
                    any(),
                    slugForPopulatedResponse
                )
            } returns Single.just(populatedFeedItemResponseList)
            every { mApiService.getFeedPage(any(), slugForEmptyResponse) } returns Single.just(
                emptyFeedItemResponseList
            )
            every { mApiService.getFeedPage(any(), slugForExceptionResponse) } returns Single.error(
                Exception()
            )
        }

        @BeforeEach
        fun mockFeedItemMapper() {
            every { mFeedItemMapper.convertList(populatedFeedItemResponseList) } returns populatedFeedItemList
            every { mFeedItemMapper.convertList(emptyFeedItemResponseList) } returns emptyFeedItemList
        }

        @Test
        fun returnsEmptyListForEmptyResponse() {

            val testObserver: TestObserver<List<FeedItem>> =
                mApiHelperImplementation.getFeedPage(somePage, slugForEmptyResponse).test()

            testObserver.assertValue(
                emptyFeedItemList
            )
        }

        @Test
        fun returnsValidPopulatedListForPopulatedResponse() {
            val testObserver: TestObserver<List<FeedItem>> =
                mApiHelperImplementation.getFeedPage(somePage, slugForPopulatedResponse).test()

            testObserver.assertValue(
                populatedFeedItemList
            )
        }

        @Test
        fun passesExceptionDownStreamWhenGetsExceptionFromAPI() {

            val testObserver: TestObserver<List<FeedItem>> =
                mApiHelperImplementation.getFeedPage(somePage, slugForExceptionResponse).test()

            testObserver.assertError(Exception::class.java)
        }
    }

    @Nested
    inner class GetAllSportTest {

        private val populatedSportResponseList = listOf(
            SportResponse(
                1, "SportSlug1", "SportName1", "https://IconURL1"
            ),
            SportResponse(
                2, "SportSlug2", "SportName2", "https://IconURL2"
            )
        )
        private val emptySportResponseList = listOf<SportResponse>()

        private val populatedSportList = listOf(
            Sport(
                1, "SportSlug1", "SportName1", "https://IconURL1"
            ),
            Sport(
                2, "SportSlug2", "SportName2", "https://IconURL2"
            )
        )
        private val emptySportList = listOf<Sport>()

        @BeforeEach
        fun mockSportItemMapper() {
            every { mSportItemMapper.convertList(populatedSportResponseList) } returns populatedSportList
            every { mSportItemMapper.convertList(emptySportResponseList) } returns emptySportList
        }

        @Test
        fun returnsEmptyListForEmptyResponse() {
            every { mApiService.getAllSports() } returns Single.just(populatedSportResponseList)

            val testObserver: TestObserver<List<Sport>> =
                mApiHelperImplementation.getAllSport().test()

            testObserver.assertValue(
                populatedSportList
            )
        }

        @Test
        fun returnsValidPopulatedListForPopulatedResponse() {
            every { mApiService.getAllSports() } returns Single.just(emptySportResponseList)

            val testObserver: TestObserver<List<Sport>> =
                mApiHelperImplementation.getAllSport().test()

            testObserver.assertValue(
                emptySportList
            )
        }

        @Test
        fun passesExceptionDownStreamWhenGetsExceptionFromAPI() {
            every { mApiService.getAllSports() } returns Single.error(java.lang.Exception())

            val testObserver: TestObserver<List<Sport>> =
                mApiHelperImplementation.getAllSport().test()

            testObserver.assertError(Exception::class.java)
        }
    }
}
