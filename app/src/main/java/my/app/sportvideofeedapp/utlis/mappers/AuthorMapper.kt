package my.app.sportvideofeedapp.utlis.mappers

import my.app.sportvideofeedapp.data.entities.Author
import my.app.sportvideofeedapp.data.network.pojo.AuthorResponse

object AuthorMapper : BasicListMapper<AuthorResponse, Author>() {
    override fun convert(objectToConvert: AuthorResponse): Author {
        return Author(
            objectToConvert.id,
            objectToConvert.name
        )
    }
}
