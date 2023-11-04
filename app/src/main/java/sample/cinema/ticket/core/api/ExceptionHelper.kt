package sample.cinema.ticket.core.api

import sample.cinema.ticket.R

object ExceptionHelper {

    fun getError(exception: Exceptions): ErrorView {

        val serverErrorMessage: String?
        val message: Int

        when (exception) {
            is Exceptions.IOException -> {
                serverErrorMessage = null
                message = R.string.error_server
            }
            is Exceptions.NetworkConnectionException -> {
                serverErrorMessage = null
                message = R.string.error_connection
            }
            is Exceptions.LocalDataSourceException -> {
                serverErrorMessage = null
                message = R.string.error_general
            }
            is Exceptions.RemoteDataSourceException -> {
                serverErrorMessage =
                    if (exception.message.isNullOrEmpty()) null else exception.message
                message = R.string.error_server
            }
            else -> {
                serverErrorMessage = null
                message = R.string.error_general
            }
        }
        return ErrorView(
            serverErrorMessage = serverErrorMessage,
            message = message
        )
    }

    data class ErrorView(
        val serverErrorMessage: String?,
        val message: Int
    )
}
