package sample.cinema.ticket.core.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import sample.cinema.ticket.core.theme.LargeRadius
import sample.cinema.ticket.core.util.extensions.appShadow

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) = Box(
    modifier = modifier
        .appShadow(cornersRadius = LargeRadius)
        .clip(shape = shape)
        .background(color = backgroundColor)
) {
    content()
}
