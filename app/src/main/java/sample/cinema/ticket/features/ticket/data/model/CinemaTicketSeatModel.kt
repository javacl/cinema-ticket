package sample.cinema.ticket.features.ticket.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CinemaTicketSeatModel(
    val id: Int,
    val available: Boolean
)
