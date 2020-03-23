package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.network.pojo.SportResponse

object SportMapper : BasicListMapper<SportResponse, Sport>() {
    override fun convert(objectToConvert: SportResponse): Sport {
        return Sport(
            objectToConvert.id,
            objectToConvert.slug,
            objectToConvert.name,
            objectToConvert.iconUrl
        )
    }
}
