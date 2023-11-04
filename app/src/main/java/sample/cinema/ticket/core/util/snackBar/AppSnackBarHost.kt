package sample.cinema.ticket.core.util.snackBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sample.cinema.ticket.R
import sample.cinema.ticket.core.theme.w400
import sample.cinema.ticket.core.theme.w600
import sample.cinema.ticket.core.theme.x2
import sample.cinema.ticket.core.util.ui.AppCard

@Composable
fun AppSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onRefresh: (() -> Unit)? = null
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { snackBar ->

            val isRetry = snackBar.visuals.duration == SnackbarDuration.Indefinite

            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                backgroundColor = MaterialTheme.colorScheme.error
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {},
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                            .weight(1f),
                        text = snackBar.visuals.message,
                        style = MaterialTheme.typography.w600.x2,
                        color = MaterialTheme.colorScheme.onError
                    )

                    Text(
                        modifier = Modifier
                            .clickable {
                                if (isRetry)
                                    onRefresh?.invoke()

                                snackBar.dismiss()
                            }
                            .padding(16.dp),
                        text = stringResource(id = if (isRetry) R.string.label_retry else R.string.label_i_realized),
                        style = MaterialTheme.typography.w400.x2,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    )
}
