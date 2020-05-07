package my.app.sportvideofeedapp.data.network.pojo

import com.google.gson.annotations.SerializedName

data class AthleteResponse(
    val id: Int,
    val age: Int,
    val name: String,
    @SerializedName("avatar")
    val avatarUrl: String,
    val club: String,
    val isCelebrity: Boolean,
    val country: CountryResponse,
    val sport: SportResponse
)
