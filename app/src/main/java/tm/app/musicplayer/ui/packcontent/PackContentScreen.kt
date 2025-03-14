package tm.app.musicplayer.ui.packcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import tm.app.musicplayer.ui.common.MusicItem
import tm.app.musicplayer.ui.home.component.tm.app.musicplayer.ui.packcontent.PackContentEvent
import tm.app.musicplayer.ui.home.component.tm.app.musicplayer.ui.packcontent.PackContentUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackContentScreen(
    modifier: Modifier,
    uiState: StateFlow<PackContentUiState>,
    onEvent: (PackContentEvent) -> Unit,
) {
    val state by uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onEvent(PackContentEvent.FetchData)
    }
    if (state.errorMessage == null) {
        if (state.data != null && state.data!!.musics.isNotEmpty()) {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = state.data!!.name,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium),
                            )
                        }
                    )
                },
                contentWindowInsets = WindowInsets(0)
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    itemsIndexed(state.data!!.musics) { index, music ->
                        MusicItem(
                            music = music,
                            onClick = {
                                onEvent(PackContentEvent.OnMusicSelected(index))
                            }
                        )
                    }
                }

            }

        } else if (state.loading) {
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
        } else {
            // TODO: empty screen ui
        }

    } else {
        // TODO: Error UI\
    }


}