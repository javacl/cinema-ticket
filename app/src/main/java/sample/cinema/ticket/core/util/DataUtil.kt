package sample.cinema.ticket.core.util

import sample.cinema.ticket.features.ticket.data.model.CinemaTicketDayModel
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel
import sample.cinema.ticket.features.ticket.ui.cinema.CinemaTicketStatusType
import kotlin.random.Random

object DataUtil {

    fun cinemaTicketSeatList(): List<CinemaTicketSeatModel> {

        val list = arrayListOf<CinemaTicketSeatModel>()

        for (i in 1..30) {
            list.add(
                CinemaTicketSeatModel(
                    id = i,
                    available = Random.nextBoolean(),
                    selected = false
                )
            )
        }

        return list
    }

    fun cinemaTicketStatusTypeList() = listOf(
        CinemaTicketStatusType.Available,
        CinemaTicketStatusType.Taken,
        CinemaTicketStatusType.Selected
    )

    fun cinemaTicketDayList() = listOf(
        CinemaTicketDayModel(
            day = "14",
            name = "Thu"
        ),
        CinemaTicketDayModel(
            day = "15",
            name = "Fri"
        ),
        CinemaTicketDayModel(
            day = "16",
            name = "Sat"
        ),
        CinemaTicketDayModel(
            day = "17",
            name = "Sun"
        ),
        CinemaTicketDayModel(
            day = "18",
            name = "Mon"
        ),
        CinemaTicketDayModel(
            day = "19",
            name = "Tue"
        ),
        CinemaTicketDayModel(
            day = "20",
            name = "Wed"
        )
    )

    fun cinemaTicketHourList() = listOf(
        "10:00",
        "12:30",
        "15:30",
        "18:30",
        "20:30",
        "21:30",
        "22:00",
        "23:30",
        "01:30",
        "02:00"
    )
}
