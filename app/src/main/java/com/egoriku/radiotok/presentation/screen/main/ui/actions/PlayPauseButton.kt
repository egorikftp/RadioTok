package com.egoriku.radiotok.presentation.screen.main.ui.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.radiotok.R
import com.egoriku.radiotok.foundation.button.CircleIconButtonLarge
import com.egoriku.radiotok.presentation.ui.RadioTokTheme

@Preview(showBackground = true)
@Composable
private fun PlayPauseActionPreview() {
    RadioTokTheme {
        Row {
            PlayPauseAction(isPlaying = true, enable = true) {}
            PlayPauseAction(isPlaying = false, enable = true) {}
            PlayPauseAction(isPlaying = true, enable = false) {}
            PlayPauseAction(isPlaying = false, enable = false) {}
        }
    }
}

@Composable
fun PlayPauseAction(
    isPlaying: Boolean,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    val icon = when {
        isPlaying -> painterResource(id = R.drawable.ic_pause)
        else -> painterResource(id = R.drawable.ic_play)
    }

    CircleIconButtonLarge(
        background = if (enable) MaterialTheme.colors.primary else Color.Gray.copy(alpha = 0.2f),
        icon = icon,
        onClick = onClick
    )
}