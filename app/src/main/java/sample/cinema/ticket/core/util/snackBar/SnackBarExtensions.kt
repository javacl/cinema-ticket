package sample.cinema.ticket.core.util.snackBar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

suspend fun SnackbarHostState.showAppSnackBar(
    message: String?,
    type: SnackBarType = SnackBarType.Error,
    duration: SnackbarDuration,
) {
    if (!message.isNullOrEmpty()) {
        currentSnackbarData?.dismiss()
        showSnackbar(
            message = message,
            actionLabel = type.name,
            duration = duration
        )
    }
}
