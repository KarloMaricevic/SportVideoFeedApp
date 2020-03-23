package my.app.sportvideofeedapp.data.network

import io.reactivex.Single
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport

interface ApiHelper {
    fun getFeedPage(page: Int = 1, sportSlug: String): Single<List<FeedItem>>
    fun getAllSport(): Single<List<Sport>>
}
