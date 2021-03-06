package my.app.sportvideofeedapp.core.views

interface NetworkView {
    fun handleHTTPUnauthorized()
    fun handleHTTPForbidden()
    fun handleInternalError()
    fun handleHTTPBadRequest()
    fun handleJsonSyntaxException()
    fun handleOtherError()
}
