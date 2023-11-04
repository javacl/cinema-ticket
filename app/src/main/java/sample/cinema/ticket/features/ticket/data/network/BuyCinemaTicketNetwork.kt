package sample.cinema.ticket.features.ticket.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuyCinemaTicketNetwork(
    val status: String = ""
)
