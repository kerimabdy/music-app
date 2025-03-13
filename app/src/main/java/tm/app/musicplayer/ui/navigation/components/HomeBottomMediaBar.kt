package tm.app.musicplayer.ui.home.component.tm.app.musicplayer.ui.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.flow.StateFlow
import tm.app.musicplayer.R
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.MusicControllerEvent
import tm.app.musicplayer.other.MusicControllerUiState
import tm.app.musicplayer.other.PlayerState
import tm.app.musicplayer.other.toTime

@Composable
fun HomeBottomMediaBar(
    onEvent: (MusicControllerEvent) -> Unit,
    onBarClick: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: StateFlow<MusicControllerUiState>,

) {
    val state by uiState.collectAsStateWithLifecycle()

    AnimatedVisibility(
        visible = state.playerState != PlayerState.STOPPED && state.currentMusic != null
    ) {
        Box(
            modifier = modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            BottomMediaBarItem(
                music = state.currentMusic!!,
                onBarClick = onBarClick,
                onEvent = onEvent,
                isPlaying = state.playerState == PlayerState.PLAYING,
                duration = state.totalDuration
            )
        }
    }
}

@Composable
private fun BottomMediaBarItem(
    music: Music,
    onBarClick: () -> Unit,
    onEvent: (MusicControllerEvent) -> Unit,
    isPlaying: Boolean = false,
    duration: Long
) {
    Surface(
        modifier = Modifier.navigationBarsPadding(),
        onClick = onBarClick,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BottomMediaBarThumbnail(onEvent = onEvent, music.thumbnail, music.title, isPlaying)

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    music.title.trim(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    music.artist.trim().toUpperCase(LocaleList.current),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.75f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Text(
                    text = duration.toTime(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                )
            }
        }
    }
}

@Composable
private fun BottomMediaBarThumbnail(
    onEvent: (MusicControllerEvent) -> Unit,
    thumbnail: String,
    contentDescription: String,
    isPlaying: Boolean,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(
                onClick = { onEvent(MusicControllerEvent.PlayPauseToggle(isPlaying)) }
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .width(40.dp)
                .aspectRatio(1f),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(0.5f))
        ) {
            val painter = if (isPlaying) {
                painterResource(R.drawable.pause)
            } else {
                painterResource(R.drawable.play)
            }
            Icon(
                painter = painter,
                contentDescription = "Play status",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }
    }
}