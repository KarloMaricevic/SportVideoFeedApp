package my.app.sportvideofeedapp.data.network.pojo

import com.google.gson.annotations.SerializedName

data class SportResponse(
    val id: Int,
    val slug: String,
    val name: String,
    @SerializedName("icon")
    val iconUrl: String
)
