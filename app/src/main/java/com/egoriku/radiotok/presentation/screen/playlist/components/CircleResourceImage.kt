package com.egoriku.radiotok.presentation.screen.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleResourceImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    size: Dp,
    iconSize: Dp = size,
    tintColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .border(5.dp, MaterialTheme.colors.primary, CircleShape)

    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(size = iconSize),
            colorFilter = ColorFilter.tint(tintColor)
        )
    }
}