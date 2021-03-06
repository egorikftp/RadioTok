package com.egoriku.radiotok.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val RadioColors = lightColors(
    primary = Color(0xFFEE894F),
    onPrimary = Color(0xFFF4EFE7),
    primaryVariant = Color(0xFFEE894F),
    secondary = Color(0xFF393939),
    onSecondary = Color(0xFFF4EFE7),
    error = Color.Red,
    onError = Color(0xFFF4EFE7)
)

@Composable
fun RadioTokTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        RadioColors
    } else {
        RadioColors
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}