package com.egoriku.radiotok.presentation.screen

import androidx.navigation.NavHostController

class Navigator(private val navHostController: NavHostController) {

    val openSettings: () -> Unit = {
        navHostController.navigate(NavScreen.Settings.route)
    }

    val openPlaylist: () -> Unit = {
        navHostController.navigate(NavScreen.Playlist.route)
    }
}