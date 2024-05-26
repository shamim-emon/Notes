package bd.emon.notes.common

sealed class Response {
    data object Loading : Response()
    data class Success<T>(val data: T?) : Response()
    data class Error(val message: String) : Response()
}