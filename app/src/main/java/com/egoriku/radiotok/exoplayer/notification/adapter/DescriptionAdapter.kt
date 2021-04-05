package com.egoriku.radiotok.exoplayer.notification.adapter

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaControllerCompat
import coil.imageLoader
import coil.request.ImageRequest
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class DescriptionAdapter(
    private val context: Context,
    private val mediaController: MediaControllerCompat
) : PlayerNotificationManager.MediaDescriptionAdapter {

    private val description: MediaDescriptionCompat
        get() = mediaController.metadata.description

    override fun getCurrentContentTitle(player: Player) = description.title.toString()

    override fun createCurrentContentIntent(player: Player): PendingIntent? =
        mediaController.sessionActivity

    override fun getCurrentContentText(player: Player) = description.subtitle.toString()

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val imageRequest = ImageRequest.Builder(context)
            .data(mediaController.metadata.description.iconUri)
            .target { result ->
                callback.onBitmap((result as BitmapDrawable).bitmap)
            }
            .build()

        context.imageLoader.enqueue(imageRequest)

        return null
    }
}