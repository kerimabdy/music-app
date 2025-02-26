package tm.app.musicplayer.ui.home.component

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import tm.app.musicplayer.R
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.PlayerState
import tm.app.musicplayer.ui.home.HomeEvent


@Composable
fun MusicItem(
    onClick: () -> Unit,
    music: Music
) {
    Log.d("Music Info", music.toString())
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)



    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable(onClick = { onClick() })
                .fillMaxSize()
                .padding(8.dp)


        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(music.thumbnailUrl))
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = music.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxHeight()
                    .aspectRatio(1f)


                ,
            )
//            Image(
//                painter = painter,
//                contentDescription = music.name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .clip(RoundedCornerShape(12.dp))
//                    .fillMaxHeight()
//                    .aspectRatio(1f)
////                    .background(Color.Black)
//            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    music.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    music.artist,
                    style = MaterialTheme.typography.labelLarge,
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
                },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More button",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
                    modifier = Modifier.size(32.dp)
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
            thumbnailUrl = "thumbnail"
        ),
    )
}