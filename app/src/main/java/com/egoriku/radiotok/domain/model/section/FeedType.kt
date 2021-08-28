package com.egoriku.radiotok.domain.model.section

sealed class FeedType {

    data class InstantPlay(
        val mediaId: String,
        val name: String,
        val icon: Int
    ) : FeedType()

    data class Playlist(
        val name: String,
        val icon: Int
    ) : FeedType()

    data class SimplePlaylist(
        val name: String,
        val count: String
    ) : FeedType()
}