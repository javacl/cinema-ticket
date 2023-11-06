package sample.cinema.ticket.features.ticket.domain

import sample.cinema.ticket.core.api.ApiResult
import sample.cinema.ticket.core.api.Exceptions
import sample.cinema.ticket.core.util.ValidateKeys
import sample.cinema.ticket.features.ticket.data.TicketRepository
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel
import sample.cinema.ticket.features.ticket.data.param.BuyCinemaTicketParam
import sample.cinema.ticket.features.ticket.domain.validation.BuyCinemaTicketValidationError
import javax.inject.Inject

class DoBuyCinemaTicket @Inject constructor(
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke(
        selectedSeatList: List<CinemaTicketSeatModel>,
        day: String,
        hour: String
    ): ApiResult<Boolean> {
        val validate = validateBuyCinemaTicket(
            selectedSeatList = selectedSeatList,
            day = day,
            hour = hour
        )
        return if (validate.isValid()) {

            val seatIds = selectedSeatList.map { it.id }

            ticketRepository.buyCinemaTicket(
                buyCinemaTicketParam = BuyCinemaTicketParam(
                    seatIds = seatIds,
                    day = day,
                    hour = hour
                )
            )
        } else ApiResult.Error(Exceptions.ValidationException(validate))
    }

    private fun validateBuyCinemaTicket(
        selectedSeatList: List<CinemaTicketSeatModel>,
        day: String,
        hour: String
    ): BuyCinemaTicketValidationError {

        val validate = BuyCinemaTicketValidationError()

        if (selectedSeatList.isEmpty())
            validate.message.add(ValidateKeys.SelectSeat)
        else if (day.isEmpty())
            validate.message.add(ValidateKeys.SelectDay)
        else if (hour.isEmpty())
            validate.message.add(ValidateKeys.SelectHour)

        return validate
    }
}
