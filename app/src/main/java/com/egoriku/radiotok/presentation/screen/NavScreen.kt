package com.egoriku.radiotok.presentation.screen

sealed class NavScreen(val route: String) {

    object Feed : NavScreen(route = "feed")
    object Settings : NavScreen(route = "settings")
    object Playlist : NavScreen(route = "playlist")
}