package tm.app.musicplayer.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import tm.app.musicplayer.R
import tm.app.musicplayer.domain.model.Music


@Composable
fun MusicItem(
    music: Music,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    isPlaying: Boolean = false,
    isDownloaded: Boolean = false
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = RoundedCornerShape(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MusicItemIndicator(music.thumbnail, music.title, isSelected, isPlaying)


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

            if (isDownloaded) {
                Box {
                    Icon(
                        painter = painterResource(R.drawable.arrow_inbox),
                        contentDescription = "Downloaded",
                        tint = Color(48, 209, 88),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MusicItemIndicator(
    thumbnail: String,
    contentDescription: String,
    isSelected: Boolean,
    isPlaying: Boolean
){
    Box(
        modifier = Modifier.size(40.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(2.dp)).width(40.dp ).aspectRatio(1f),
        )
        if (isSelected) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(0.3f))
            ) {
                val painter = if (isPlaying) {
                    painterResource(R.drawable.play)
                } else {
                    painterResource(R.drawable.pause)
                }
                Icon(
                    painter = painter,
                    contentDescription = "Play status",
                    modifier = Modifier.align(Alignment.Center).size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MusicItemPreview() {
    MusicItem(
        onClick = {},
        music = Music(
            id = 1,
            title = "Music 1",
            artist = "Artist name",
            url = "song",
            thumbnail = "thumbnail",
        ),
        isSelected = true,
        isPlaying = false
    )
}