package my.app.sportvideofeedapp.data.network.pojo

import com.google.gson.annotations.SerializedName

data class FeedItemResponse(
    val id: String,
    val createdAt: String,
    val createdBefore: String,
    val description: String,
    val author: AuthorResponse,
    val sportGroup: SportsGroupResponse,
    val video: VideoResponse,
    val athlete: AthleteResponse,
    @SerializedName("bookmarked")
    val isBookmarked: Boolean,
    @SerializedName("views")
    val whoWatched: String
)
