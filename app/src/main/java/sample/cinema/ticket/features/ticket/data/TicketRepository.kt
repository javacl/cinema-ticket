package sample.cinema.ticket.features.ticket.data

import sample.cinema.ticket.core.api.ApiResult
import sample.cinema.ticket.core.api.Exceptions
import sample.cinema.ticket.core.util.NetworkHandler
import sample.cinema.ticket.features.ticket.data.param.BuyCinemaTicketParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepository @Inject constructor(
    private val ticketLocalDataSource: TicketLocalDataSource,
    private val ticketRemoteDataSource: TicketRemoteDataSource,
    private val networkHandler: NetworkHandler
) {
    suspend fun buyCinemaTicket(buyCinemaTicketParam: BuyCinemaTicketParam): ApiResult<Boolean> {
        return if (networkHandler.hasNetworkConnection()) {
            when (val result = ticketRemoteDataSource.buyCinemaTicket(buyCinemaTicketParam)) {
                is ApiResult.Success -> ApiResult.Success(true)
                is ApiResult.Error -> ApiResult.Error(result.exception, false)
            }
        } else ApiResult.Error(Exceptions.NetworkConnectionException())
    }
}
