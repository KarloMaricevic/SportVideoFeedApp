package my.app.sportvideofeedapp.utils.mappers

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import my.app.sportvideofeedapp.data.entities.Video
import my.app.sportvideofeedapp.data.network.pojo.VideoResponse
import my.app.sportvideofeedapp.utlis.converters.SecondsToMinutesConverter
import my.app.sportvideofeedapp.utlis.mappers.VideoMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class VideoMapperTest(
    @MockK private val mSecondsToMinutesConverter: SecondsToMinutesConverter

) {

    private val videoMapper = VideoMapper(mSecondsToMinutesConverter)

    @BeforeEach
    fun mockSecondsToMinutesConverter() {
        every { mSecondsToMinutesConverter.convertSecondsToMinutesAndSeconds(allAny()) } returns "1:25"
    }

    @Nested
    inner class SingleVideoResponseMapTest {

        @TestFactory
        fun getValidVideoForVideoResponse(): Collection<DynamicTest> {
            val lengthOfVideoInSeconds = "125"

            val videoResponse = VideoResponse(
                "handlerName",
                "https://videoURL",
                "https://thumbnailURL",
                "videoType",
                lengthOfVideoInSeconds
            )

            val expectedValue = Video(
                "handlerName",
                "https://videoURL",
                "https://thumbnailURL",
                "videoType",
                "1:25"
            )

            val actualValue = videoMapper.convert(videoResponse)

            return listOf(
                DynamicTest.dynamicTest("goodHandlerMap") {
                    Assertions.assertEquals(expectedValue.handler, actualValue.handler)
                },
                DynamicTest.dynamicTest("goodVideoUrlMap") {
                    Assertions.assertEquals(expectedValue.videoUrl, actualValue.videoUrl)
                },
                DynamicTest.dynamicTest("goodThumbnailUrl") {
                    Assertions.assertEquals(expectedValue.thumbnailUrl, actualValue.thumbnailUrl)
                },
                DynamicTest.dynamicTest("goodTypeMap") {
                    Assertions.assertEquals(expectedValue.type, actualValue.type)
                },
                DynamicTest.dynamicTest("goodLengthMap") {
                    Assertions.assertEquals(expectedValue.length, actualValue.length)
                }
            )
        }
    }
}
