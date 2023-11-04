package sample.cinema.ticket.features.ticket.domain

import sample.cinema.ticket.features.ticket.data.TicketRepository
import sample.cinema.ticket.features.ticket.data.param.BuyCinemaTicketParam
import javax.inject.Inject

class DoBuyCinemaTicket @Inject constructor(
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke(
        seatIds: List<String>,
        day: String,
        hour: String
    ) = ticketRepository.buyCinemaTicket(
        buyCinemaTicketParam = BuyCinemaTicketParam(
            seatIds = seatIds,
            day = day,
            hour = hour
        )
    )
}
