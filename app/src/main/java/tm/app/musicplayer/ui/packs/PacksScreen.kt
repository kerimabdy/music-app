package tm.app.musicplayer.ui.packs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.flow.StateFlow
import tm.app.musicplayer.R

@Composable
fun PacksScreen(
    modifier: Modifier = Modifier,
    uiState: StateFlow<PacksUiState>,
    onNavigateToPackContent: (Int) -> Unit
) {
    val state by uiState.collectAsStateWithLifecycle()

    when (state) {
        is PacksUiState.Loading -> {
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

        is PacksUiState.Error -> {
            TODO()
        }

        is PacksUiState.Success -> {
            LazyVerticalGrid(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp)),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                items((state as PacksUiState.Success).data) { item ->
                    PackItem(
                        onNavigateToPackContent = onNavigateToPackContent,
                        id = item.id,
                        item.name,
                        item.musicsCount,
                        cover = item.cover
                    )
                }
            }
        }

    }
}

@Composable
fun PackItem(
    onNavigateToPackContent: (Int) -> Unit,
    id: Int,
    name: String,
    musicsCount: Int,
    cover: String,
) {

    Surface(
        onClick = { onNavigateToPackContent(id) },
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text(
                    name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "$musicsCount TRACKS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
            Box() {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cover)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize()
                        .aspectRatio(1f),
                )
                Spacer(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .size(20.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                )
            }
        }
    }
}