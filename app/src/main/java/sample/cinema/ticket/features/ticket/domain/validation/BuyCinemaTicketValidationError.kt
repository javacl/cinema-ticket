package sample.cinema.ticket.features.ticket.domain.validation

import sample.cinema.ticket.core.util.ValidateKeys

class BuyCinemaTicketValidationError {

    val message: ArrayList<ValidateKeys> = ArrayList()

    fun isValid(): Boolean {
        return message.size <= 0
    }
}
