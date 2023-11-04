package sample.cinema.ticket.core.util.navigation


sealed class NavigationRoutes(val route: String) {

    // Main
    data object Root : NavigationRoutes(
        route = "root"
    )

    // Ticket
    data object CinemaTicket : NavigationRoutes(
        route = "cinema_ticket"
    )
}
