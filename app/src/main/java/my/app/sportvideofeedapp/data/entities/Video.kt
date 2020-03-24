package my.app.sportvideofeedapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val handler: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val type: String,
    val length: String
) : Parcelable
