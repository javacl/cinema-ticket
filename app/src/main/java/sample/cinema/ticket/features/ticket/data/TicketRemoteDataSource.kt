package sample.cinema.ticket.features.ticket.data

import sample.cinema.ticket.core.api.BaseRemoteDataSource
import sample.cinema.ticket.core.api.safeApiCall
import sample.cinema.ticket.features.ticket.data.param.BuyCinemaTicketParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRemoteDataSource @Inject constructor(
    private val ticketService: TicketService
) : BaseRemoteDataSource() {

    suspend fun buyCinemaTicket(
        buyCinemaTicketParam: BuyCinemaTicketParam
    ) = safeApiCall(
        call = {
            requestBuyCinemaTicket(
                buyCinemaTicketParam = buyCinemaTicketParam
            )
        },
        errorMessage = "Error buy cinema ticket"
    )

    private suspend fun requestBuyCinemaTicket(
        buyCinemaTicketParam: BuyCinemaTicketParam
    ) = checkApiResult(
        ticketService.buyCinemaTicket(
            buyCinemaTicketParam = buyCinemaTicketParam
        )
    )
}
