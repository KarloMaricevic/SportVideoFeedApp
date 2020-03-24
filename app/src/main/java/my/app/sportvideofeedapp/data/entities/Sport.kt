package my.app.sportvideofeedapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sport(
    val id: Int,
    val slug: String,
    val name: String,
    val iconUrl: String
) : Parcelable
