package sample.cinema.ticket.features.ticket.ui.cinema

import dagger.hilt.android.lifecycle.HiltViewModel
import sample.cinema.ticket.core.util.viewModel.BaseViewModel
import sample.cinema.ticket.features.ticket.domain.DoBuyCinemaTicket
import javax.inject.Inject

@HiltViewModel
class CinemaTicketViewModel @Inject constructor(
    private val doBuyCinemaTicket: DoBuyCinemaTicket
) : BaseViewModel() {
}
