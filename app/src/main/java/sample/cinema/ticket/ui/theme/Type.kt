package sample.cinema.ticket.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography()

val BaseTextStyle = TextStyle(
    lineHeight = 24.sp
)

val Typography.w100: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W100
    )

val Typography.w200: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W200
    )

val Typography.w300: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W300
    )

val Typography.w400: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W400
    )

val Typography.w500: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W500
    )

val Typography.w600: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W600
    )

val Typography.w700: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W700
    )

val Typography.w800: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W800
    )

val Typography.w900: TextStyle
    get() = BaseTextStyle.copy(
        fontWeight = FontWeight.W900
    )

val TextStyle.x1: TextStyle
    get() = copy(
        fontSize = 10.sp
    )

val TextStyle.x2: TextStyle
    get() = copy(
        fontSize = 12.sp
    )

val TextStyle.x3: TextStyle
    get() = copy(
        fontSize = 14.sp
    )

val TextStyle.x4: TextStyle
    get() = copy(
        fontSize = 16.sp
    )

val TextStyle.x5: TextStyle
    get() = copy(
        fontSize = 18.sp
    )

val TextStyle.x6: TextStyle
    get() = copy(
        fontSize = 20.sp
    )

val TextStyle.x7: TextStyle
    get() = copy(
        fontSize = 22.sp
    )

val TextStyle.x8: TextStyle
    get() = copy(
        fontSize = 24.sp
    )
