package tm.app.musicplayer.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(

        title = {
            Row {
                Text(
                    text = "Listen to Music",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier
                        .padding(start = 8.dp),
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    HomeAppBar()
}