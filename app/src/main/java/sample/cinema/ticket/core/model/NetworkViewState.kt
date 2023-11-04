package sample.cinema.ticket.core.model

import sample.cinema.ticket.R

data class NetworkViewState(
    var showProgress: Boolean = false,
    var showProgressMore: Boolean = false,
    var showSuccess: Boolean = false,
    var showError: Boolean = false,
    var showValidationError: Boolean = false,
    var serverErrorMessage: String? = null,
    var errorMessage: Int = R.string.error_general,
    var requestTag: String? = null,
    var validationError: Any? = null,
    var data: Any? = null
)
