package com.egoriku.radiotok.presentation.screen.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.extension.currentFraction
import com.egoriku.radiotok.foundation.SheetContent
import com.egoriku.radiotok.presentation.ControlsActions
import com.egoriku.radiotok.presentation.RadioViewModel
import com.egoriku.radiotok.presentation.screen.NavScreen
import com.egoriku.radiotok.presentation.screen.Navigator
import com.egoriku.radiotok.presentation.screen.feed.FeedScreen
import com.egoriku.radiotok.presentation.screen.playlist.PlaylistScreen
import com.egoriku.radiotok.presentation.screen.settings.SettingScreen
import com.egoriku.radiotok.presentation.ui.radio.miniplayer.RadioMiniPlayer
import com.egoriku.radiotok.presentation.ui.radio.player.RadioPlayer
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel: RadioViewModel) {
    val currentPlayingRadio by viewModel.currentPlayingRadio.collectAsState()
    val playbackState by viewModel.playbackState.collectAsState()

    val navController = rememberAnimatedNavController()
    val scope = rememberCoroutineScope()

    val controlsActions = remember { ControlsActions(viewModel) }
    val navigator = remember { Navigator(navHostController = navController) }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )

    val sheetToggle: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isCollapsed) {
                scaffoldState.bottomSheetState.expand()
            } else {
                scaffoldState.bottomSheetState.collapse()
            }
        }
    }

    val radius = (30 * scaffoldState.currentFraction).dp

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        sheetContent = {
            SheetContent {
                RadioPlayer(
                    radioItemModel = currentPlayingRadio,
                    playbackState = playbackState,
                    controlsActions = controlsActions,
                    fraction = scaffoldState.currentFraction
                )

                RadioMiniPlayer(
                    radioItem = currentPlayingRadio,
                    playbackState = playbackState,
                    controlsActions = controlsActions,
                    fraction = scaffoldState.currentFraction,
                    clickable = scaffoldState.bottomSheetState.isCollapsed,
                    onClick = sheetToggle
                )
            }
        },
        sheetPeekHeight = 72.dp
    ) { paddingValues ->
        AnimatedNavHost(
            navController = navController,
            startDestination = NavScreen.Feed.route
        ) {
            composable(NavScreen.Feed.route) {
                FeedScreen(
                    paddingValues = paddingValues,
                    navigator = navigator
                )
            }
            composable(NavScreen.Settings.route) {
                SettingScreen()
            }
            composable(NavScreen.Playlist.route) {
                PlaylistScreen()
            }
        }
    }
}