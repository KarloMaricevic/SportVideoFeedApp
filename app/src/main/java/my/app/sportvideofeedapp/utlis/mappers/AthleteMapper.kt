package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Athlete
import my.app.sportvideofeedapp.data.entities.Country
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.network.pojo.AthleteResponse
import my.app.sportvideofeedapp.data.network.pojo.CountryResponse
import my.app.sportvideofeedapp.data.network.pojo.SportResponse
import javax.inject.Inject

class AthleteMapper @Inject constructor(private val countryMapper : BasicListMapper<CountryResponse, Country>, private val sportMapper: BasicListMapper<SportResponse,Sport>) : BasicListMapper<AthleteResponse, Athlete>() {
    override fun convert(objectToConvert: AthleteResponse): Athlete {
        return Athlete(objectToConvert.id,
            objectToConvert.age,
            objectToConvert.name,
            objectToConvert.avatarUrl,
            objectToConvert.club,
            objectToConvert.isCelebrity,
            countryMapper.convert(objectToConvert.country),
            sportMapper.convert(objectToConvert.sport)
            )
    }
}
