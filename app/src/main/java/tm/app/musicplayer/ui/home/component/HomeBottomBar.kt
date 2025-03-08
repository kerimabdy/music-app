package tm.app.musicplayer.ui.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tm.app.musicplayer.R
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.PlayerState
import tm.app.musicplayer.ui.home.HomeEvent
import tm.app.musicplayer.ui.home.HomeUiState


@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    uiState: StateFlow<HomeUiState>,
    onBarClick: () -> Unit
) {

    val state by uiState.collectAsState()
    var offsetX by remember { mutableFloatStateOf(0f) }

    AnimatedVisibility(
        visible = playerState != PlayerState.STOPPED,
        modifier = modifier
    ) {
        if (state.musics != null && state.musics!!.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = {
                state.musics!!.size
            })

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.settledPage }.collect { page ->
//                    onEvent(HomeEvent.OnMusicSelected(state.musics!![page]))
//                    onEvent(HomeEvent.PlayMusic(page))
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) { pageIdx ->
                    HomeBottomBarItem(
                        music = state.musics!![pageIdx],
                        onEvent = onEvent,
                        playerState = playerState,
                        onBarClick = onBarClick,
                    )

                }

            }
        }
    }
}


@Composable
fun HomeBottomBarItem(
    background: Color = Color.Gray,
    music: Music,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    onBarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
            .height(64.dp)


    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = { onBarClick() })
                .background(background)
                .fillMaxSize()
                .padding(8.dp)


        ) {
            Image(
                painter = rememberAsyncImagePainter(music.thumbnail),
                contentDescription = music.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(Color.Black)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    music.title!!,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    music.artist,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 0.60f
                        }

                )
            }


            IconButton(
                onClick = {
                    if (playerState == PlayerState.PLAYING) {
                        onEvent(HomeEvent.PauseMusic)
                    } else {
                        onEvent(HomeEvent.ResumeMusic)
                    }
                },
            ) {
                val painter = rememberAsyncImagePainter(
                    if (playerState == PlayerState.PLAYING) {
                        R.drawable.ic_round_pause
                    } else {
                        R.drawable.ic_round_play_arrow
                    }
                )
                Icon(
                    painter = painter,
                    contentDescription = "Play/pause button",
                    modifier = Modifier
                    .size(48.dp)
                )

            }
        }
    }
}


@Preview
@Composable
fun HomeBottomBarPreview() {
    HomeBottomBar(
        onEvent = {  },
        playerState = PlayerState.PAUSED,
        onBarClick = {  },
        uiState = MutableStateFlow(
            HomeUiState(
                musics = listOf(
                    Music(
                        id = 1,
                        title = "Music 1",
                        artist = "Artist name",
                        url = "song",
                        thumbnail = "thumbnail"
                    ),
                    Music(
                        id = 2,
                        title = "Music 2",
                        artist = "Artist name",
                        url = "song",
                        thumbnail = "thumbnail"
                    ),
                    Music(
                        id = 3,
                        title = "Music 3",
                        artist = "Artist name",
                        url = "song",
                        thumbnail = "thumbnail"
                    )
                ),
            )
        )

    )
}

@Preview
@Composable
fun HomeBottomBarItemPreview() {
    HomeBottomBarItem(
        onEvent = {  },
        playerState = PlayerState.PAUSED,
        music = Music(
            id = 1,
            title = "Music anme",
            artist = "Artist name",
            url = "song",
            thumbnail = "thumbnail"
        ),
        onBarClick = {  },
        background = Color.Gray
    )
}