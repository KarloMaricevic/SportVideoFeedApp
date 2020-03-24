package my.app.sportvideofeedapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
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
) : Parcelable
