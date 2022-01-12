package com.egoriku.radiotok.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import com.egoriku.radiotok.presentation.screen.main.MainScreen
import com.egoriku.radiotok.presentation.ui.RadioTokTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RadioTokTheme {
                ProvideWindowInsets {
                    Navigator(screen = MainScreen)
                }
            }
        }
    }
}