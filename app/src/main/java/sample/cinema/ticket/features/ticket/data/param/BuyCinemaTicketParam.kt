package sample.cinema.ticket.features.ticket.data.param

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuyCinemaTicketParam(
    val seatIds: List<Int>,
    val day: String,
    val hour: String
)
