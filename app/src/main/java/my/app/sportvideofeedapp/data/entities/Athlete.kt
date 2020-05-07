package my.app.sportvideofeedapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Athlete(
    val id: Int,
    val age: Int,
    val name: String,
    val avatarUrl: String,
    val club: String,
    val isCelebrity: Boolean,
    val country: Country,
    val sport: Sport
) : Parcelable
