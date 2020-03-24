package my.app.sportvideofeedapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val id: Int,
    val name: String,
    val slug: String,
    val iconUrl: String
) : Parcelable
