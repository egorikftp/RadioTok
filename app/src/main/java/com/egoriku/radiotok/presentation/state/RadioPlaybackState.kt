package com.egoriku.radiotok.presentation.state

data class RadioPlaybackState(
    val isPlaying: Boolean = false,
    val isPrepared: Boolean = false,
    val isPlayEnabled: Boolean = false
)