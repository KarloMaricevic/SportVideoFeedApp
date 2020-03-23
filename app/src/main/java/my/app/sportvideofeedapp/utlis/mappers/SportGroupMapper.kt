package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.SportGroup
import my.app.sportvideofeedapp.data.network.pojo.SportsGroupResponse

object SportGroupMapper : BasicListMapper<SportsGroupResponse, SportGroup>() {
    override fun convert(objectToConvert: SportsGroupResponse): SportGroup {
        return SportGroup(
            objectToConvert.id,
            objectToConvert.name
        )
    }
}
