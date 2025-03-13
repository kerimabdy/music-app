package tm.app.musicplayer.ui.navigation.components

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
import androidx.media3.common.Label
import tm.app.musicplayer.ui.navigation.TopLevelRoute

data class HomeTabOption(
    val title: String,
    val subtitle: String,
    val onNavigate: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabRow(
    selectedTabIndex: () -> Int,
    tabs: @Composable () -> Unit
) {
    SecondaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex(),
        edgePadding = 0.dp,
        indicator = {},
        divider = {},
        tabs = tabs
    ) 
}

@Composable
fun HomeTabItem(
    label: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Tab (
        modifier = Modifier.height(72.dp).padding(horizontal = 16.dp),
        selected = isSelected,
        selectedContentColor = MaterialTheme.colorScheme.surface,
        onClick = { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(
                alpha = if (isSelected) 1f else .75f
            )
        )
    }
}

@Composable
@Preview
fun HomeTabRowPreview() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    HomeTabRow(
        selectedTabIndex = { selectedTabIndex },
        tabs = TODO()
    )
}


