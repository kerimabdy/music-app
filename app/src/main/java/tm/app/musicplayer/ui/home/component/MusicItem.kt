package tm.app.musicplayer.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import tm.app.musicplayer.domain.model.Music


@Composable
fun MusicItem(
    onClick: () -> Unit,
    music: Music
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
    ) {

        Spacer(
            Modifier.fillMaxWidth().height(1.dp).background(Color.Gray)
        )

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(music.songUrl)
                    .crossfade(true).build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Text(
            text = music.name,
            maxLines = 2,
            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.weight(1f)
        )

        Text(
            text = music.artist,
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier.weight(1f)
        )
    }
}