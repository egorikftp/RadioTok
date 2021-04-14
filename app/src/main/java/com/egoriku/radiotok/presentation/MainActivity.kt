package com.egoriku.radiotok.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.radiotok.presentation.theme.RadioTokTheme
import com.egoriku.radiotok.presentation.ui.radio.RadioScreen
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private val viewModel: RadioViewModel by viewModel()

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RadioTokTheme {
                val currentPlayingRadio by viewModel.currentPlayingRadio.collectAsState()
                val playbackState by viewModel.playbackState.collectAsState()

                val controlsActions = remember { ControlsActions(viewModel) }

                if (currentPlayingRadio.id.isEmpty()) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    RadioScreen(
                        radioItemModel = currentPlayingRadio,
                        playbackState = playbackState,
                        controlsActions = controlsActions
                    )
                }
            }
        }
    }
}

class ControlsActions(viewModel: RadioViewModel) {

    val dislikeRadioStationEvent: () -> Unit = { viewModel.dislikeRadioStation() }
    val tuneRadiosEvent: () -> Unit = {}
    val playPauseEvent: () -> Unit = { viewModel.togglePlayPause() }
    val nextRadioEvent: () -> Unit = { viewModel.nextRadioStation() }
    val addRemoveFavoriteEvent: () -> Unit = { viewModel.likeRadioStation() }
}