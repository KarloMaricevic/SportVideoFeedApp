package my.app.sportvideofeedapp.data.network.pojo

import com.google.gson.annotations.SerializedName

data class SinglePostResponse(
    val id: Int,
    val createdAt: String,
    val createBefore: String,
    val author: AuthorResponse,
    val sportsGroup: SportsGroupResponse,
    val video: VideoResponse,
    val description: String,
    val athlete: AthleteResponse,
    @SerializedName("bookmarked")
    val isBookmarked: Boolean,
    @SerializedName("views")
    val whoWatched: String
)
