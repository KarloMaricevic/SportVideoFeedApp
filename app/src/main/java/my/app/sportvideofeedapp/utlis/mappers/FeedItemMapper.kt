package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Athlete
import my.app.sportvideofeedapp.data.entities.Author
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.SportGroup
import my.app.sportvideofeedapp.data.entities.Video
import my.app.sportvideofeedapp.data.network.pojo.AthleteResponse
import my.app.sportvideofeedapp.data.network.pojo.AuthorResponse
import my.app.sportvideofeedapp.data.network.pojo.FeedItemResponse
import my.app.sportvideofeedapp.data.network.pojo.SportsGroupResponse
import my.app.sportvideofeedapp.data.network.pojo.VideoResponse
import my.app.sportvideofeedapp.utlis.converters.StringToDateConverter
import javax.inject.Inject

class FeedItemMapper @Inject constructor(
    private val authorMapper: BasicListMapper<AuthorResponse, Author>,
    private val athleteMapper: BasicListMapper<AthleteResponse, Athlete>,
    private val sportGroupMapper: BasicListMapper<SportsGroupResponse, SportGroup>,
    private val videoMapper: BasicListMapper<VideoResponse, Video>,
    private val stringToDateConverter: StringToDateConverter
) : BasicListMapper<FeedItemResponse, FeedItem>() {
    override fun convert(objectToConvert: FeedItemResponse): FeedItem {

        return FeedItem(
            objectToConvert.id,
            stringToDateConverter.convertStringToDate(objectToConvert.createdAt),
            objectToConvert.createdBefore,
            objectToConvert.description,
            authorMapper.convert(objectToConvert.author),
            sportGroupMapper.convert(objectToConvert.sportGroup),
            videoMapper.convert(objectToConvert.video),
            athleteMapper.convert(objectToConvert.athlete),
            objectToConvert.isBookmarked,
            objectToConvert.whoWatched
        )
    }
}
