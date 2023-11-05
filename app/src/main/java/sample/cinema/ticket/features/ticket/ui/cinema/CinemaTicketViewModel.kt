package sample.cinema.ticket.features.ticket.ui.cinema

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import sample.cinema.ticket.core.util.DataUtil
import sample.cinema.ticket.core.util.extensions.splitIntoParts
import sample.cinema.ticket.core.util.viewModel.BaseViewModel
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketDayModel
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel
import sample.cinema.ticket.features.ticket.domain.DoBuyCinemaTicket
import javax.inject.Inject

@HiltViewModel
class CinemaTicketViewModel @Inject constructor(
    private val doBuyCinemaTicket: DoBuyCinemaTicket
) : BaseViewModel() {

    private val seatList = MutableStateFlow(DataUtil.cinemaTicketSeatList())

    val selectedSeatList = seatList.map {
        it.filter { item -> item.selected }
    }

    val packedSeatList = seatList.map { it.splitIntoParts(2) }

    private val _dayList = MutableStateFlow(DataUtil.cinemaTicketDayList())
    val dayList = _dayList.asStateFlow()

    private val _selectedDay = MutableStateFlow<CinemaTicketDayModel?>(null)
    val selectedDay = _selectedDay.asStateFlow()

    private val _hourList = MutableStateFlow(DataUtil.cinemaTicketHourList())
    val hourList = _hourList.asStateFlow()

    private val _selectedHour = MutableStateFlow<String?>(null)
    val selectedHour = _selectedHour.asStateFlow()

    fun toggleSeatSelection(value: CinemaTicketSeatModel) {

        val list = seatList.value.toMutableList()

        try {
            val index = list.indexOf(value)

            list[index] = value.copy(
                selected = !value.selected
            )
        } catch (_: Exception) {
        }

        seatList.value = list
    }
}
