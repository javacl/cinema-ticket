package sample.cinema.ticket.core.util.extensions

import android.content.Context
import sample.cinema.ticket.core.model.NetworkViewState
import sample.cinema.ticket.core.util.ValidateKeys

fun <T> List<T>.splitIntoParts(partSize: Int): List<List<T>> {

    val parts = mutableListOf<List<T>>()

    for (i in indices step partSize) {
        val end = minOf(i + partSize, size)
        parts.add(subList(i, end))
    }

    return parts
}

fun Context.parseValidationErrorMessage(errorList: ArrayList<ValidateKeys>?): String? {
    return if (errorList.isNullOrEmpty()) null else getString(errorList.first().value)
}

fun Context.parseServerErrorMessage(networkViewState: NetworkViewState): String {
    return networkViewState.serverErrorMessage ?: getString(networkViewState.errorMessage)
}
