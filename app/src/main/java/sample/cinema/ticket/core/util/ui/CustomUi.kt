package sample.cinema.ticket.core.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sample.cinema.ticket.core.theme.LargeRadius
import sample.cinema.ticket.core.theme.w700
import sample.cinema.ticket.core.theme.x2
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

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    isWrap: Boolean = false,
    wrapPadding: Dp = if (isWrap) 32.dp else 0.dp,
    isBorder: Boolean = false,
    borderWidth: Dp = 1.dp,
    enabled: Boolean = true,
    loading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = MaterialTheme.shapes.large,
    text: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.w700.x2,
    iconResource: Int? = null,
    iconSize: Dp = 16.dp,
    iconPadding: Dp = if (text.isNullOrEmpty()) 0.dp else 8.dp,
    onClick: () -> Unit
) {
    val isEnabled = enabled && !loading

    val mBackgroundColor = if (isEnabled) backgroundColor else disabledBackgroundColor

    val mContentColor = if (isEnabled) {

        if (isBorder) backgroundColor else contentColor

    } else {

        disabledContentColor
    }

    Row(
        modifier = modifier
            .then(
                if (isWrap) {
                    Modifier.wrapContentWidth()
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .height(height)
            .appShadow(cornersRadius = LargeRadius)
            .clip(shape)
            .then(
                if (isBorder) {
                    Modifier.border(
                        width = borderWidth,
                        color = mBackgroundColor,
                        shape = shape
                    )
                } else {
                    Modifier.background(mBackgroundColor)
                }
            )
            .clickable(
                onClick = onClick,
                enabled = isEnabled
            )
            .padding(horizontal = wrapPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if (loading) {

            AppCircularProgressIndicator(
                color = mContentColor
            )

        } else {

            iconResource?.let {
                Icon(
                    modifier = Modifier
                        .padding(end = iconPadding)
                        .size(iconSize),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = mContentColor
                )
            }

            text?.let {
                Text(
                    text = it,
                    style = textStyle,
                    color = mContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AppCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) = CircularProgressIndicator(
    modifier = modifier.size(16.dp),
    color = color,
    strokeWidth = 2.dp
)
