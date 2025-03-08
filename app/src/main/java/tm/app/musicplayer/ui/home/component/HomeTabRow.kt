package tm.app.musicplayer.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class HomeTabOption(
    val title: String,
    val subtitle: String,
    val onNavigate: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabRow(
    options: List<HomeTabOption>,
    selectedTabIndex: () -> Int
) {
    SecondaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex(),
        edgePadding = 0.dp,
        indicator = {},
        divider = {}
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedTabIndex()
            Tab(
                modifier = Modifier.height(64.dp),
                selected = isSelected,
                onClick = { option.onNavigate() },
                selectedContentColor = MaterialTheme.colorScheme.surface,

            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ){
                    Text(
                        option.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = if (isSelected) 1f else .75f
                        )
                    )
                    Text(
                        option.subtitle,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = .75f
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeTabRowPreview() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    HomeTabRow(
        options = listOf(
            HomeTabOption(
                title = "Packs",
                subtitle = "250",
                onNavigate = {
                    selectedTabIndex = 0
                }
            ),
            HomeTabOption(
                title = "Tracks",
                subtitle = "450",
                onNavigate = {
                    selectedTabIndex = 1
                }
            ),
            HomeTabOption(
                title = "Downloaded",
                subtitle = "2",
                onNavigate = {
                    selectedTabIndex = 2
                }
            )
        ),
        selectedTabIndex = { selectedTabIndex }
    )
}