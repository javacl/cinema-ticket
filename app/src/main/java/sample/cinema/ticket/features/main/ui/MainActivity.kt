package sample.cinema.ticket.features.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sample.cinema.ticket.core.theme.AppTheme
import sample.cinema.ticket.core.util.snackBar.showAppSnackBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val coroutineScope = rememberCoroutineScope()

            val mainSnackBarHostState = remember { SnackbarHostState() }

            AppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        mainSnackBarHostState = mainSnackBarHostState,
                        showMainSnackBar = { message, duration ->

                            coroutineScope.launch {

                                mainSnackBarHostState.showAppSnackBar(
                                    message = message,
                                    duration = duration
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
