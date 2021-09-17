package com.egoriku.radiotok.presentation.screen.playlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.radiotok.foundation.button.IconButton
import com.egoriku.radiotok.presentation.screen.Navigator
import com.egoriku.radiotok.presentation.screen.playlist.components.CircleResourceImage
import com.egoriku.radiotok.presentation.screen.playlist.components.RadioListItem
import com.egoriku.radiotok.presentation.screen.playlist.components.ShuffleButton
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun PlaylistScreen(
    id: String,
    paddingValues: PaddingValues,
    navigator: Navigator
) {
    val playlistViewModel = getViewModel<PlaylistViewModel>()

    LaunchedEffect(key1 = Unit) {
        playlistViewModel.load(id)
    }

    val playlistState by playlistViewModel.playlistState.collectAsState()

    when (val playlistState: PlaylistState = playlistState) {
        is PlaylistState.Loading -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier)
                }
            }
        }
        is PlaylistState.Error -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error loading")
                }
            }
        }
        is PlaylistState.Success -> {
            val state = rememberCollapsingToolbarScaffoldState()

            CollapsingToolbarScaffold(
                modifier = Modifier.fillMaxSize(),
                state = state,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbarModifier = Modifier.statusBarsPadding(),
                toolbar = {
                    val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .pin()
                    )

                    CircleResourceImage(
                        modifier = Modifier
                            .road(Alignment.Center, Alignment.Center)
                            .graphicsLayer {
                                alpha = state.toolbarState.progress
                            },
                        size = 150.dp,
                        iconSize = 50.dp,
                        tintColor = Color.White,
                        painter = painterResource(id = playlistState.playlist.icon)
                    )

                    Text(
                        text = playlistState.playlist.title,
                        modifier = Modifier
                            .road(Alignment.TopCenter, Alignment.BottomCenter)
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        fontSize = textSize
                    )

                    IconButton(
                        modifier = Modifier
                            .pin()
                            .padding(start = 8.dp),
                        imageVector = Icons.Default.Close,
                    ) {
                        navigator.back()
                    }
                }
            ) {
                LazyColumn(
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.systemBars,
                        applyTop = true,
                        applyBottom = true,
                        additionalTop = 70.dp,
                        additionalBottom = paddingValues.calculateBottomPadding()
                    ),
                ) {
                    items(playlistState.playlist.radioStations) {
                        RadioListItem(
                            radioItemModel = it
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ShuffleButton()
                }
            }
        }
    }
}