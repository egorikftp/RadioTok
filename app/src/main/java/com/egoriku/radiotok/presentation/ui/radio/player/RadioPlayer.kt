package com.egoriku.radiotok.presentation.ui.radio.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.presentation.ControlsActions
import com.egoriku.radiotok.presentation.state.RadioPlaybackState
import com.egoriku.radiotok.presentation.ui.radio.controls.PlayerControls
import com.egoriku.radiotok.presentation.ui.radio.player.component.RadioLogo
import com.egoriku.radiotok.presentation.ui.radio.player.component.RadioPlayerContent

@Composable
fun RadioPlayer(
    radioItemModel: RadioItemModel,
    playbackState: RadioPlaybackState,
    controlsActions: ControlsActions,
    fraction: Float
) {
    RadioPlayerContent(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = fraction),
        topSection = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioLogo(url = radioItemModel.icon)

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 48.dp, start = 16.dp, end = 16.dp),
                    text = radioItemModel.name,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h6
                )
            }
        },
        playerControls = {
            PlayerControls(
                modifier = Modifier.padding(vertical = 16.dp),
                isPlaying = playbackState.isPlaying,
                isLiked = playbackState.isLiked,
                isError = playbackState.isError,
                controlsActions = controlsActions
            )
        }
    )
}