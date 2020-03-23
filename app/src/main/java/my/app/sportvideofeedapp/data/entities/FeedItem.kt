package my.app.sportvideofeedapp.data.entities

import java.util.Date

data class FeedItem(
    val id: String,
    val createdAt: Date,
    val createBefore: String,
    val description: String,
    val author: Author,
    val sportsGroup: SportGroup,
    val video: Video,
    val athlete: Athlete,
    val isBookmarked: Boolean,
    val whoWatched: String
)
