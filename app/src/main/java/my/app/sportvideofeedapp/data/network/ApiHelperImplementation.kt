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

    override fun getAllSport(): Single<List<Sport>> {
        return apiService.getAllSports()
            .subscribeOn(appSchedulerProvider.io())
            .map { sportItemMapper.convertList(it) }
    }
}
