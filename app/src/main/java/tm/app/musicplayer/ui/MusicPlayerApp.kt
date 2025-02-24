package tm.app.musicplayer.ui

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import tm.app.musicplayer.ui.home.HomeEvent
import tm.app.musicplayer.ui.home.HomeScreen
import tm.app.musicplayer.ui.home.HomeViewModel
import tm.app.musicplayer.ui.home.component.HomeBottomBar
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
    val activity = LocalActivity

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
            music = musicControllerUiState.currentMusic,
            onBarClick = {}
        )
    }


//    NavHost(
//        navController = navController,
//        graph = NavGraph().,
//    )
}
