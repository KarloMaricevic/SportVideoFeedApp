package my.app.sportvideofeedapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SportGroup(
    val id: Int,
    val name: String
) : Parcelable
