package my.app.sportvideofeedapp.data.network.pojo

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val handler: String,
    @SerializedName("url")
    val videoUrl: String,
    @SerializedName("poster")
    val thumbnailUrl: String,
    val type: String,
    @SerializedName("length")
    val lengthInSeconds: String
)
