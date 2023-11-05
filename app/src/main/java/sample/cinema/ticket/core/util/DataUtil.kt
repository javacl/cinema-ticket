package sample.cinema.ticket.core.util

import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel
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
}
