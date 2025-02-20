package tm.app.musicplayer.presentation.browseMusic

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun BrowseMusicScreen(
    modifier: Modifier = Modifier,
    viewModel: BrowseMusicViewModel = koinViewModel()
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->

        val state by viewModel.state.collectAsState()

        LazyColumn(
            contentPadding = innerPadding
        ) {
            items(
                state.data
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}