package sample.cinema.ticket.features.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sample.cinema.ticket.core.util.navigation.NavigationRoutes
import sample.cinema.ticket.core.util.snackBar.AppSnackBarHost
import sample.cinema.ticket.features.ticket.ui.cinema.CinemaTicketScreen

@Composable
fun MainScreen(
    mainSnackBarHostState: SnackbarHostState,
    showMainSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            AppSnackBarHost(
                hostState = mainSnackBarHostState
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.CinemaTicket.route,
            route = NavigationRoutes.Root.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(
                route = NavigationRoutes.CinemaTicket.route
            ) {
                CinemaTicketScreen(
                    navController = navController
                )
            }
        }
    }
}
