package tm.app.musicplayer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import org.koin.androidx.compose.koinViewModel
import tm.app.musicplayer.ui.home.TracksScreen
import tm.app.musicplayer.ui.home.TracksViewModel
import tm.app.musicplayer.ui.home.component.tm.app.musicplayer.ui.navigation.components.HomeBottomMediaBar
import tm.app.musicplayer.ui.navigation.components.HomeAppBar
import tm.app.musicplayer.ui.navigation.Downloads
import tm.app.musicplayer.ui.navigation.Home
import tm.app.musicplayer.ui.navigation.PackContent
import tm.app.musicplayer.ui.navigation.Packs
import tm.app.musicplayer.ui.navigation.TopLevelRoute
import tm.app.musicplayer.ui.navigation.Tracks
import tm.app.musicplayer.ui.navigation.components.HomeTabItem
import tm.app.musicplayer.ui.navigation.components.HomeTabRow
import tm.app.musicplayer.ui.packcontent.PackContentScreen
import tm.app.musicplayer.ui.packcontent.PackContentViewModel
import tm.app.musicplayer.ui.packs.PacksScreen
import tm.app.musicplayer.ui.packs.PacksViewModel
import tm.app.musicplayer.ui.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerApp(sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val topLevelRoutes = listOf(
        TopLevelRoute("Packs", Packs),
        TopLevelRoute("Tracks", Tracks),
        TopLevelRoute("Downloads", Downloads)
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                HomeAppBar(
                    scrollBehavior = scrollBehavior
                )
                AnimatedVisibility(
                    visible = navBackStackEntry?.destination?.parent?.route == Home::class.qualifiedName
                ) {
                    HomeTabRow(
                        selectedTabIndex = { selectedTabIndex }
                    ) {

                        topLevelRoutes.forEachIndexed { index, topLevelRoute ->

                            HomeTabItem(
                                label = topLevelRoute.name,
                                onClick = {
                                    navController.navigate(topLevelRoute.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                                isSelected = currentDestination?.route == topLevelRoute.route::class.qualifiedName
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            HomeBottomMediaBar(
                onEvent = sharedViewModel::onEvent,
                onBarClick = {},
                uiState = sharedViewModel.musicControllerUiState
            )
        }
    ) { innerPadding ->
        MusicPlayerNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            sharedViewModel = sharedViewModel
        )
    }


}

@Composable
fun MusicPlayerNavHost(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    val musicControllerUiState = sharedViewModel.musicControllerUiState
    NavHost(
        navController = navController,
        startDestination = Home,
//        popExitTransition = {
//            scaleOut(
//                targetScale = 0.9f,
//                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
//            )
//        },
//        popEnterTransition = {
//            EnterTransition.None
//        },
    ) {

        navigation<Home>(startDestination = Packs) {
            composable<Packs> {
                val packsViewModel = koinViewModel<PacksViewModel>()
                PacksScreen(
                    modifier = modifier,
                    uiState = packsViewModel.packsUiState,
                    onNavigateToPackContent = { id -> navController.navigate(route = PackContent(id))
                    }
                )
            }
            composable<Tracks> {
                val tracksViewModel = koinViewModel<TracksViewModel>()
                TracksScreen(
                    modifier = modifier,
                    uiState = tracksViewModel.tracksUiState,
                    onEvent = tracksViewModel::onEvent
                )
            }
            composable<Downloads> {
                Column(modifier = modifier) {
                    Text("Downloads")
                }
            }
        }

        composable<PackContent> { backStackEntry ->
            val packContentViewModel = koinViewModel<PackContentViewModel>()
            PackContentScreen(
                modifier = modifier,
                uiState = packContentViewModel.packContentUiState,
                onEvent = packContentViewModel::onEvent,
            )
        }
//        composable<Home> {
//            val mainViewModel = koinViewModel<HomeViewModel>()
//            val isInitialized = rememberSaveable { mutableStateOf(false) }
//
//            if (!isInitialized.value) {
//                LaunchedEffect(Unit) {
//                    mainViewModel.onEvent(HomeEvent.FetchMusic)
//                    isInitialized.value = true
//                }
//            }
//
//            Box(modifier = Modifier.fillMaxSize()) {
//                HomeScreen(
//                    onEvent = mainViewModel::onEvent,
//                    uiState = mainViewModel.homeUiState,
//                )
//                HomeBottomBar(
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter),
//                    onEvent = mainViewModel::onEvent,
//                    playerState = musicControllerUiState.playerState,
//                    uiState = mainViewModel.homeUiState,
//                    onBarClick = { navController.navigate(MusicScreen) }
//                )
//            }
//        }
//
//        composable<MusicScreen> {
//            val musicViewModel: MusicViewModel = koinViewModel<MusicViewModel>()
//            MusicScreen(
//                onEvent = musicViewModel::onEvent,
//                musicControllerUiState = musicControllerUiState,
//                onNavigateUp = { navController.navigateUp() }
//            )
//        }
    }
}
