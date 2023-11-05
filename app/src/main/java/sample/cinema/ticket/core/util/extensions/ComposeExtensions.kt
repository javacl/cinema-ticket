package sample.cinema.ticket.core.util.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.appShadow(
    cornersRadius: Dp = 0.dp
) = shadow(
    color = Color.Black,
    alpha = 0.04f,
    blurRadius = 32.dp,
    cornersRadius = cornersRadius
)

fun Modifier.shadow(
    color: Color,
    alpha: Float = 1f,
    blurRadius: Dp = 0.dp,
    cornersRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColorArgb = color.copy(alpha).toArgb()

    val transparentColorArgb = Color.Transparent.toArgb()

    val cornerRadiusPx = cornersRadius.toPx()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColorArgb

        frameworkPaint.setShadowLayer(
            blurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColorArgb
        )

        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornerRadiusPx,
            cornerRadiusPx,
            paint
        )
    }
}
