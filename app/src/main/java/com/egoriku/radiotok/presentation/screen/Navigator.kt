package com.egoriku.radiotok.presentation.screen

import androidx.navigation.NavHostController

class Navigator(private val navHostController: NavHostController) {

    val openSettings: () -> Unit = {
        navHostController.navigate(NavScreen.Settings.route)
    }

    val openPlaylist: (String) -> Unit = {
        navHostController.navigate(
            NavScreen.Playlist.route.replace("{id}", it)
        )
    }

    val back: () -> Unit = {
        navHostController.navigateUp()
    }
}