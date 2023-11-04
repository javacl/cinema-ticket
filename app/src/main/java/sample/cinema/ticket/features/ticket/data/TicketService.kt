package sample.cinema.ticket.features.ticket.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import sample.cinema.ticket.features.ticket.data.network.BuyCinemaTicketNetwork
import sample.cinema.ticket.features.ticket.data.param.BuyCinemaTicketParam

interface TicketService {

    @POST("d80d097b-9d1f-4d30-8041-fa98e8eeae70")
    suspend fun buyCinemaTicket(
        @Body buyCinemaTicketParam: BuyCinemaTicketParam
    ): Response<BuyCinemaTicketNetwork>
}
