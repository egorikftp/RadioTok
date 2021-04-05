package com.egoriku.radiotok.presentation.ui.radio.controls

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.egoriku.radiotok.R
import com.egoriku.radiotok.foundation.CircleIconButtonLarge

enum class PlayPauseState(val icon: Int) {
    Play(icon = R.drawable.ic_play_arrow),
    Pause(icon = R.drawable.ic_pause)
}

@Composable
fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    val state = when {
        isPlaying -> PlayPauseState.Pause
        else -> PlayPauseState.Play
    }

    CircleIconButtonLarge(
        background = MaterialTheme.colors.primary,
        icon = painterResource(state.icon),
        onClick = onClick
    )
}