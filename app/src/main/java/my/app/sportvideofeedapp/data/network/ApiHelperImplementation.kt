package my.app.sportvideofeedapp.data.network

import io.reactivex.Single
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.network.pojo.FeedItemResponse
import my.app.sportvideofeedapp.data.network.pojo.SportResponse
import my.app.sportvideofeedapp.utlis.mappers.BasicListMapper
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelperImplementation @Inject constructor(
    private val apiService: ApiService,
    private val appSchedulerProvider: SchedulerProvider,
    private val feedItemMapper: BasicListMapper<FeedItemResponse, FeedItem>,
    private val sportItemMapper: BasicListMapper<SportResponse, Sport>
) : ApiHelper {
    override fun getFeedPage(page: Int, sportSlug: String): Single<List<FeedItem>> {
        return apiService.getFeedPage(page, sportSlug)
            .subscribeOn(appSchedulerProvider.io())
            .map { feedItemMapper.convertList(it) }
    }

    override fun getFeedPages(
        startPage: Int,
        howMany: Int,
        sportSlug: String
    ): Single<List<FeedItem>> {
        var pageNumber = startPage
        val observablePageList = arrayListOf<Single<List<FeedItem>>>()
        do {
            observablePageList.add(
                getFeedPage(pageNumber, sportSlug)
            )
            pageNumber++
        } while (pageNumber < howMany + startPage)

        return Single.zip(observablePageList) {
            val combinedList = arrayListOf<FeedItem>()
            for (itemArray in it) {
                if (itemArray is ArrayList<*>) {
                    for (item in itemArray) {
                        if (item is FeedItem) combinedList.add(item)
                    }
                }
            }
            return@zip combinedList.toList()
        }
    }

    override fun getAllSport(): Single<List<Sport>> {
        return apiService.getAllSports()
            .subscribeOn(appSchedulerProvider.io())
            .map { sportItemMapper.convertList(it) }
    }
}
