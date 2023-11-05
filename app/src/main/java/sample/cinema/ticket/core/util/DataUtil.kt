package sample.cinema.ticket.core.util

import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel

object DataUtil {

    fun cinemaTicketSeatList() : List<CinemaTicketSeatModel> = listOf(
        CinemaTicketSeatModel(
            id = 1,
            available = true
        )
    )
}
