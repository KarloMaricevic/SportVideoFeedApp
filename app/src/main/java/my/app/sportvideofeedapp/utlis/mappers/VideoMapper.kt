package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Video
import my.app.sportvideofeedapp.data.network.pojo.VideoResponse
import my.app.sportvideofeedapp.utlis.converters.SecondsToMinutesConverter
import javax.inject.Inject

class VideoMapper @Inject constructor(private val mSecondsToMinutesConverter: SecondsToMinutesConverter) :
    BasicListMapper<VideoResponse, Video>() {
    override fun convert(objectToConvert: VideoResponse): Video {
        return Video(
            objectToConvert.handler,
            objectToConvert.videoUrl,
            objectToConvert.thumbnailUrl,
            objectToConvert.type,
            mSecondsToMinutesConverter.convertSecondsToMinutesAndSeconds(objectToConvert.lengthInSeconds)
        )
    }
}
