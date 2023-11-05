package sample.cinema.ticket.core.util.snackBar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

suspend fun SnackbarHostState.showAppSnackBar(
    message: String,
    duration: SnackbarDuration
) {
    if (message.isNotEmpty()) {
        currentSnackbarData?.dismiss()
        showSnackbar(
            message = message,
            duration = duration
        )
    }
}
