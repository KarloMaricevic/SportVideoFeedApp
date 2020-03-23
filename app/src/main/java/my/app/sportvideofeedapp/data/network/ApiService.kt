package my.app.sportvideofeedapp.data.network

import io.reactivex.Single
import my.app.sportvideofeedapp.data.network.pojo.FeedItemResponse
import my.app.sportvideofeedapp.data.network.pojo.SportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("feed")
    fun getFeedPage(@Query("page") page: Int = 1, @Query("sportSlug") sportId: String): Single<List<FeedItemResponse>>

    @GET("sport")
    fun getAllSports(): Single<List<SportResponse>>
}
