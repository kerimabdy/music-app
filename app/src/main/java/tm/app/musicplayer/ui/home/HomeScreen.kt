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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import tm.app.musicplayer.ui.home.component.HomeAppBar
import tm.app.musicplayer.ui.home.component.HomeTabOption
import tm.app.musicplayer.ui.home.component.HomeTabRow
import tm.app.musicplayer.ui.home.component.MusicItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit,
    uiState: StateFlow<HomeUiState>,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                HomeAppBar(
                    scrollBehavior = scrollBehavior
                )
                HomeTabRow(
                    options = listOf(
                        HomeTabOption(
                            title = "Packs",
                            subtitle = "250",
                            onNavigate = {
                                selectedTabIndex = 0
                            }
                        ),
                        HomeTabOption(
                            title = "Tracks",
                            subtitle = "450",
                            onNavigate = {
                                selectedTabIndex = 1
                            }
                        ),
                        HomeTabOption(
                            title = "Downloaded",
                            subtitle = "2",
                            onNavigate = {
                                selectedTabIndex = 2
                            }
                        )
                    ),
                    selectedTabIndex = { selectedTabIndex }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)).padding(innerPadding).fillMaxHeight()
        ) {
            LazyColumn(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            ) {
                items(100) {
                    Spacer(Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh).fillMaxWidth().height(56.dp))
                    Spacer(Modifier.background(MaterialTheme.colorScheme.onSurface).fillMaxWidth().height(1.dp))
                }
            }
        }
    }

//    Surface(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.fillMaxWidth()) {
//            val appBarColor = MaterialTheme.colorScheme.surface
//            Spacer(
//                Modifier
//                    .background(appBarColor)
//                    .fillMaxWidth()
//                    .windowInsetsTopHeight(WindowInsets.statusBars)
//            )
//            HomeAppBar(
//                modifier = Modifier.fillMaxWidth()
//            )
//
//
//            when {
//                state.loading == true -> {
//                    Box(modifier = Modifier.fillMaxSize()) {
//                        CircularProgressIndicator(
//                            color = MaterialTheme.colorScheme.onBackground,
//                            modifier = Modifier
//                                .size(100.dp)
//                                .fillMaxHeight()
//                                .align(Alignment.Center)
//                                .padding(
//                                    top = 16.dp,
//                                    start = 16.dp,
//                                    end = 16.dp,
//                                    bottom = 16.dp
//                                )
//                        )
//                    }
//                }
//
//                state.loading == false && state.errorMessage == null -> {
//                    val musics = state.musics
//                    Log.d("MusicInHomeScreen", musics.toString())
//
//                    if (musics != null) {
//
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize(),
//                            contentAlignment = Alignment.BottomCenter
//                        )
//                        {
//                            LazyColumn(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .background(MaterialTheme.colorScheme.background)
//                                    .align(Alignment.TopCenter),
//                                contentPadding = PaddingValues(bottom = 60.dp)
//                            ) {
//                                itemsIndexed(musics) { index, music ->
//
//                                    MusicItem(
//                                        onClick = {
//                                            onEvent(HomeEvent.OnMusicSelected(music))
//                                            onEvent(HomeEvent.PlayMusic(index))
//                                        },
//                                        music = music
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                state.errorMessage != null -> {
//                }
//            }
//        }
//    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onEvent = {  },
        uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    )
}