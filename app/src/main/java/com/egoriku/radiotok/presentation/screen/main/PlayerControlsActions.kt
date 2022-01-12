package com.egoriku.radiotok.presentation.screen.main

import com.egoriku.radiotok.presentation.RadioViewModel

class PlayerControlsActions(viewModel: RadioViewModel) {

    val dislikeRadioStationEvent: () -> Unit = { viewModel.dislikeRadioStation() }
    val tuneRadiosEvent: () -> Unit = {}
    val playPauseEvent: () -> Unit = { viewModel.togglePlayPause() }
    val nextRadioEvent: () -> Unit = { viewModel.nextRadioStation() }
    val toggleFavoriteEvent: () -> Unit = { viewModel.likeRadioStation() }
}