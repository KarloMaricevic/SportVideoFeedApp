package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Athlete
import my.app.sportvideofeedapp.data.entities.Author
import my.app.sportvideofeedapp.data.entities.SinglePost
import my.app.sportvideofeedapp.data.entities.SportGroup
import my.app.sportvideofeedapp.data.entities.Video
import my.app.sportvideofeedapp.data.network.pojo.AthleteResponse
import my.app.sportvideofeedapp.data.network.pojo.AuthorResponse
import my.app.sportvideofeedapp.data.network.pojo.SinglePostResponse
import my.app.sportvideofeedapp.data.network.pojo.SportsGroupResponse
import my.app.sportvideofeedapp.data.network.pojo.VideoResponse
import my.app.sportvideofeedapp.utlis.converters.StringToDateConverter
import javax.inject.Inject

class SinglePostMapper @Inject constructor(
    private val authorMapper: BasicListMapper<AuthorResponse, Author>,
    private val athleteMapper: BasicListMapper<AthleteResponse, Athlete>,
    private val sportGroupMapper: BasicListMapper<SportsGroupResponse, SportGroup>,
    private val videoMapper: BasicListMapper<VideoResponse, Video>,
    private val stringToDateConverter: StringToDateConverter
) : BasicListMapper<SinglePostResponse, SinglePost>() {
    override fun convert(objectToConvert: SinglePostResponse): SinglePost {
        return SinglePost(
            objectToConvert.id,
            stringToDateConverter.convertStringToDate(objectToConvert.createdAt),
            objectToConvert.createBefore,
            authorMapper.convert(objectToConvert.author),
            sportGroupMapper.convert(objectToConvert.sportsGroup),
            videoMapper.convert(objectToConvert.video),
            objectToConvert.description,
            athleteMapper.convert(objectToConvert.athlete),
            objectToConvert.isBookmarked,
            objectToConvert.whoWatched
        )
    }
}
