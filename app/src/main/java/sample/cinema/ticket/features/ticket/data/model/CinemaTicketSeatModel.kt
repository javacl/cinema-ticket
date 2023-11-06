package sample.cinema.ticket.features.ticket.data.model

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
data class CinemaTicketSeatModel(
    val id: Int,
    val available: Boolean,
    val selected: Boolean
)
