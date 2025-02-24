package tm.app.musicplayer.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import tm.app.musicplayer.ui.home.component.HomeAppBar
import tm.app.musicplayer.ui.home.component.MusicItem

@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit,
    uiState: StateFlow<HomeUiState>,
) {
    val state by uiState.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val appBarColor = MaterialTheme.colorScheme.surface
            Spacer(
                Modifier
                    .background(appBarColor)
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )
            HomeAppBar(
                modifier = Modifier.fillMaxWidth()
            )


            when {
                state.loading == true -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(100.dp)
                                .fillMaxHeight()
                                .align(Alignment.Center)
                                .padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                        )
                    }
                }

                state.loading == false && state.errorMessage == null -> {
                    val musics = state.musics
                    Log.d("MusicInHomeScreen", musics.toString())

                    if (musics != null) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        )
                        {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .align(Alignment.TopCenter),
                                contentPadding = PaddingValues(bottom = 60.dp)
                            ) {
                                itemsIndexed(musics) { index, music ->

                                    MusicItem(
                                        onClick = {
                                            onEvent(HomeEvent.OnMusicSelected(music))
                                            onEvent(HomeEvent.PlayMusic(index))
                                        },
                                        music = music
                                    )
                                }
                            }
                        }
                    }
                }

                state.errorMessage != null -> {
                }
            }
        }
    }
}