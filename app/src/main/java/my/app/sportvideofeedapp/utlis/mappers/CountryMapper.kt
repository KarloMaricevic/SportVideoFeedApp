package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Country
import my.app.sportvideofeedapp.data.network.pojo.CountryResponse

object CountryMapper : BasicListMapper<CountryResponse, Country>() {
    override fun convert(objectToConvert: CountryResponse): Country {
        return Country(
            objectToConvert.id,
            objectToConvert.name,
            objectToConvert.slug,
            objectToConvert.iconUrl
        )
    }
}
