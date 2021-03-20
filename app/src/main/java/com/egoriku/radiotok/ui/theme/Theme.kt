package com.egoriku.radiotok.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Yellow800 = Color(0xFFF29F05)
val Red300 = Color(0xFFEA6D7E)

val RadioColors = lightColors(
    primary = Yellow800,
    onPrimary = Color.White,
    primaryVariant = Yellow800,
    secondary = Yellow800,
    onSecondary = Color.White,
    error = Red300,
    onError = Color.White
)

@Composable
fun RadioTokTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = RadioColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}