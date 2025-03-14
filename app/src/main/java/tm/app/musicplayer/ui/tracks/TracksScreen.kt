package tm.app.musicplayer.ui.tracks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tm.app.musicplayer.ui.common.MusicItem

@Composable
fun TracksScreen(
    onEvent: (TracksEvent) -> Unit,
    uiState: StateFlow<TracksUiState>,
    modifier: Modifier = Modifier
) {
    val state by uiState.collectAsStateWithLifecycle()

    if (state.errorMessage == null) {
        if (state.musics.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                itemsIndexed(state.musics) { index, music ->
                    MusicItem(
                        music = music,
                        onClick = {
                            onEvent(TracksEvent.OnMusicSelected(index))
                        }
                    )
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

@Preview
@Composable
fun TracksScreenPreview() {
    TracksScreen(
        onEvent = {  },
        uiState = MutableStateFlow(TracksUiState())
    )
}