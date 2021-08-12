package com.egoriku.radiotok.presentation.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.radiotok.presentation.ui.header.ScreenHeader
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        ScreenHeader(title = "Settings")
    }
}