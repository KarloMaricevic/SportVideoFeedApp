package my.app.sportvideofeedapp.data.interactors

import io.reactivex.Single
import my.app.sportvideofeedapp.core.Interactor
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.network.ApiHelper
import javax.inject.Inject

class FeedInteractor @Inject constructor(private val apiHelper: ApiHelper) : Interactor {
    fun getAllSports(): Single<List<Sport>> {
        return apiHelper.getAllSport()
    }

    fun getFeedPage(page: Int = 1, sportSlug: String): Single<List<FeedItem>> {
        return apiHelper.getFeedPage(page, sportSlug)
    }
}
