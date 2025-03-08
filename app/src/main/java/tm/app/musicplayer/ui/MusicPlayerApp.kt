package tm.app.musicplayer.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import tm.app.musicplayer.ui.home.HomeEvent
import tm.app.musicplayer.ui.home.HomeScreen
import tm.app.musicplayer.ui.home.HomeViewModel
import tm.app.musicplayer.ui.home.component.HomeBottomBar
import tm.app.musicplayer.ui.musicscreen.MusicScreen
import tm.app.musicplayer.ui.musicscreen.MusicViewModel
import tm.app.musicplayer.ui.navigation.Home
import tm.app.musicplayer.ui.navigation.MusicScreen
import tm.app.musicplayer.ui.viewmodels.SharedViewModel

@Composable
fun MusicPlayerApp(sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()

    MusicPlayerNavHost(
        navController = navController,
        sharedViewModel = sharedViewModel
    )
}

@Composable
fun MusicPlayerNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val musicControllerUiState = sharedViewModel.musicControllerUiState


    NavHost(
        navController = navController,
        startDestination = Home
    ){
        composable<Home> {
            val mainViewModel = koinViewModel<HomeViewModel>()
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(Unit) {
                    mainViewModel.onEvent(HomeEvent.FetchMusic)
                    isInitialized.value = true
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                )
                HomeBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onEvent = mainViewModel::onEvent,
                    playerState = musicControllerUiState.playerState,
                    uiState = mainViewModel.homeUiState,
                    onBarClick = { navController.navigate(MusicScreen) }
                )
            }
        }

        composable<MusicScreen> {
            val musicViewModel: MusicViewModel = koinViewModel<MusicViewModel>()
            MusicScreen(
                onEvent = musicViewModel::onEvent,
                musicControllerUiState = musicControllerUiState,
                onNavigateUp = { navController.navigateUp() }
            )
        }


    }
}
