package com.egoriku.radiotok.presentation.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.R
import com.egoriku.radiotok.extension.currentFraction
import com.egoriku.radiotok.extension.noRippleClickable
import com.egoriku.radiotok.foundation.SheetContent
import com.egoriku.radiotok.presentation.ControlsActions
import com.egoriku.radiotok.presentation.RadioViewModel
import com.egoriku.radiotok.presentation.ui.playlist.PlaylistScreen
import com.egoriku.radiotok.presentation.ui.radio.RadioScreen
import com.egoriku.radiotok.presentation.ui.radio.about.RadioLogoSmall
import com.egoriku.radiotok.presentation.ui.settings.SettingScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.placeholder.material.placeholder
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: RadioViewModel) {
    val currentPlayingRadio by viewModel.currentPlayingRadio.collectAsState()
    val playbackState by viewModel.playbackState.collectAsState()

    val controlsActions = remember { ControlsActions(viewModel) }

    val scope = rememberCoroutineScope()

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

    val navController = rememberAnimatedNavController()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null
                    )
                }
            }
        },
        sheetContent = {
            SheetContent {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RadioScreen(
                        radioItemModel = currentPlayingRadio,
                        playbackState = playbackState,
                        controlsActions = controlsActions
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .background(MaterialTheme.colors.primary)
                        .graphicsLayer(alpha = 1f - scaffoldState.currentFraction)
                        .noRippleClickable(
                            onClick = sheetToggle,
                            enabled = scaffoldState.bottomSheetState.isCollapsed
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioLogoSmall(
                        modifier = Modifier.padding(start = 16.dp),
                        placeholder = Modifier.placeholder(
                            visible = currentPlayingRadio.id.isEmpty()
                        ),
                        logoUrl = currentPlayingRadio.icon
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                            .placeholder(visible = currentPlayingRadio.id.isEmpty()),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = currentPlayingRadio.name,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.caption
                    )
                    IconButton(
                        onClick = { controlsActions.addRemoveFavoriteEvent() },
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
                    ) {
                        if (playbackState.isLiked) {
                            Icon(
                                painter = painterResource(R.drawable.ic_favorite),
                                tint = MaterialTheme.colors.onPrimary,
                                contentDescription = "Add favorite"
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.ic_favorite_border),
                                tint = MaterialTheme.colors.onPrimary,
                                contentDescription = "Remove favorite"
                            )
                        }
                    }
                }
            }
        },
        sheetPeekHeight = 72.dp
    ) { paddingValues ->
        AnimatedNavHost(
            navController = navController,
            startDestination = NavScreen.Home.route
        ) {
            composable(NavScreen.Home.route) {
                RadioCollectionScreen(paddingValues = paddingValues)
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

sealed class NavScreen(val route: String) {

    object Home : NavScreen("home")
    object Settings : NavScreen("settings")
    object Playlist : NavScreen("playlist")
}