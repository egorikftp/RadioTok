package com.egoriku.radiotok.radioplayer.listener

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.model.MediaPath.PlayLiked
import com.egoriku.radiotok.radioplayer.model.MediaPath.ShuffleAndPlayRoot.ShuffleLiked
import com.egoriku.radiotok.radioplayer.model.MediaPath.ShuffleAndPlayRoot.ShuffleRandom
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import kotlinx.coroutines.runBlocking

class RadioPlaybackPreparer(
    private val radioCacheMediator: IRadioCacheMediator,
    private val onPlayerPrepared: () -> Unit
) : MediaSessionConnector.PlaybackPreparer {

    override fun onCommand(
        player: Player,
        controlDispatcher: ControlDispatcher,
        command: String,
        extras: Bundle?,
        cb: ResultReceiver?
    ) = false

    override fun getSupportedPrepareActions() =
        PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
                PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH

    override fun onPrepare(playWhenReady: Boolean) = Unit

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
        logD("onPrepareFromMediaId = $mediaId")

        runBlocking {
            when (val mediaPath = MediaPath.fromParentIdOrNull(mediaId)) {
                is ShuffleLiked -> {
                    radioCacheMediator.updatePlaylist(mediaPath = mediaPath)
                    onPlayerPrepared()
                }
                is ShuffleRandom -> {
                    radioCacheMediator.updatePlaylist(mediaPath = mediaPath)
                    onPlayerPrepared()
                }
                is PlayLiked -> {
                    radioCacheMediator.updatePlaylist(mediaPath = mediaPath)
                    onPlayerPrepared()
                }
                else -> {
                    radioCacheMediator.playSingle(id = mediaId)
                    onPlayerPrepared()
                }
            }
        }
    }

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {
        logD("onPrepareFromSearch: $query")
    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit
}