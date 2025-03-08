package tm.app.musicplayer.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import tm.app.musicplayer.R




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(R.drawable.magnifying_glass_2),
                    contentDescription = "Search Button",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(R.drawable.world),
                    contentDescription = "Search Button",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(R.drawable.people),
                    contentDescription = "Search Button",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarPreview() {
    HomeAppBar()
}