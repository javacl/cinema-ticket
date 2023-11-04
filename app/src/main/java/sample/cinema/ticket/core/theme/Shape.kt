package sample.cinema.ticket.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val ExtraSmallRadius = 4.dp
val SmallRadius = 8.dp
val MediumRadius = 12.dp
val LargeRadius = 16.dp
val ExtraLargeRadius = 24.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(ExtraSmallRadius),
    small = RoundedCornerShape(SmallRadius),
    medium = RoundedCornerShape(MediumRadius),
    large = RoundedCornerShape(LargeRadius),
    extraLarge = RoundedCornerShape(ExtraLargeRadius)
)
