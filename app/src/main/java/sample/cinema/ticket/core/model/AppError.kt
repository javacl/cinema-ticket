package sample.cinema.ticket.core.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppError(
    val message: String = ""
)
