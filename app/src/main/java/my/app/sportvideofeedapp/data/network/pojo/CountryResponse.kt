package my.app.sportvideofeedapp.data.network.pojo

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    val id: Int,
    val name: String,
    val slug: String,
    @SerializedName("icon")
    val iconUrl: String
)
