package com.egoriku.radiotok.radioplayer.listener

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.model.MediaPath
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
        PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID

    override fun onPrepare(playWhenReady: Boolean) = Unit

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
        logD("onPrepareFromMediaId = $mediaId")

        when (MediaPath.fromParentIdOrNull(mediaId)) {
            is MediaPath.ShuffleAndPlayRoot.ShuffleLiked -> {
                runBlocking {
                    radioCacheMediator.switchToLikedRadios()
                    logD("liked")
                }
                onPlayerPrepared()
            }
            is MediaPath.ShuffleAndPlayRoot.ShuffleRandom -> {
                runBlocking {
                    radioCacheMediator.switchToRandomRadios()
                    logD("random")
                }
                onPlayerPrepared()
            }
            else -> {
                logD("else")

                runBlocking {
                    radioCacheMediator.playSingle(id = mediaId)
                }
                onPlayerPrepared()
            }
        }
    }

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) = Unit

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit
}