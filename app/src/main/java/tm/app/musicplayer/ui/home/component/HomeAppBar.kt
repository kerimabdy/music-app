package tm.app.musicplayer.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

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
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
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