package my.app.sportvideofeedapp.utlis.mappers

interface MapperInterface<From, To> {
    fun convert(objectToConvert: From): To
}
