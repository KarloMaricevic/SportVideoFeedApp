package my.app.sportvideofeedapp.data.entities

import java.util.Date

data class SinglePost(
    val id: Int,
    val createdAt: Date,
    val createBefore: String,
    val author: Author,
    val sportsGroup: SportGroup,
    val video: Video,
    val description: String,
    val athlete: Athlete,
    val isBookmarked: Boolean,
    val whoWatched: String
)
