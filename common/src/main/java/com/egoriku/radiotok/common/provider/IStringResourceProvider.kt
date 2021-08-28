package com.egoriku.radiotok.common.provider

interface IStringResourceProvider {

    val shuffleAndPlay: String
    val personalPlaylists: String
    val smartPlaylists: String
    val byGenres: String
    val byCountry: String
    val byLanguage: String
    val catalog: String

    val likedRadio: String
    val randomRadio: String

    val liked: String
    val recentlyPlayed: String
    val disliked: String

    val localStations: String
    val topClicks: String
    val topVote: String
    val changedLately: String
    val playing: String
}