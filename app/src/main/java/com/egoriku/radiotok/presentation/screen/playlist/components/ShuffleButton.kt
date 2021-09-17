package com.egoriku.radiotok.presentation.screen.playlist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ShuffleButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .padding(vertical = 12.dp)
            .clip(CircleShape)
    ) {
        // TODO: 17.09.21 Localize string
        Text(
            text = "PLAY All",
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}
