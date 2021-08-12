package com.egoriku.radiotok.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import com.egoriku.radiotok.presentation.screen.main.MainScreen
import com.egoriku.radiotok.presentation.theme.RadioTokTheme
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private val viewModel: RadioViewModel by viewModel()

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RadioTokTheme {
                MainScreen(viewModel)
            }
        }
    }
}

class ControlsActions(viewModel: RadioViewModel) {

    val dislikeRadioStationEvent: () -> Unit = { viewModel.dislikeRadioStation() }
    val tuneRadiosEvent: () -> Unit = {}
    val playPauseEvent: () -> Unit = { viewModel.togglePlayPause() }
    val nextRadioEvent: () -> Unit = { viewModel.nextRadioStation() }
    val toggleFavoriteEvent: () -> Unit = { viewModel.likeRadioStation() }
}