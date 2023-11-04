package sample.cinema.ticket.core.util.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

fun NavController.safeNavigate(route: String) {

    val list = route.split("/")

    val fixedRoute = StringBuilder()

    list.forEachIndexed { index, it ->
        if (!it.startsWith("{")) {
            fixedRoute.append(it)
            if (index < list.size - 1) {
                fixedRoute.append("/")
            }
        }
    }

    try {
        navigate(fixedRoute.toString())
    } catch (e: Exception) {
        Log.d("Navigate", e.toString())
    }
}

fun NavController.safeNavigate(
    route: String,
    builder: NavOptionsBuilder.() -> Unit
) {

    val list = route.split("/")

    val fixedRoute = StringBuilder()

    list.forEachIndexed { index, it ->
        if (!it.startsWith("{")) {
            fixedRoute.append(it)
            if (index < list.size - 1) {
                fixedRoute.append("/")
            }
        }
    }

    try {
        navigate(
            fixedRoute.toString(),
            navOptions(builder)
        )
    } catch (e: Exception) {
        Log.d("Navigate", e.toString())
    }
}
