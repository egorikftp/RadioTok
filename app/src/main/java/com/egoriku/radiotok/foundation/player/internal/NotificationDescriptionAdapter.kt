package com.egoriku.radiotok.foundation.player.internal

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import com.egoriku.radiotok.model.EMPTY
import com.egoriku.radiotok.model.PlayerModel
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class NotificationDescriptionAdapter(
    private val context: Context
) : PlayerNotificationManager.MediaDescriptionAdapter {

    var playerModel: PlayerModel? = null

    override fun getCurrentContentTitle(player: Player) = playerModel?.name ?: EMPTY

    override fun getCurrentContentText(player: Player): String? = null

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val imageRequest = ImageRequest.Builder(context)
            .data(playerModel?.icon)
            .target { result ->
                callback.onBitmap((result as BitmapDrawable).bitmap)
            }
            .build()

        context.imageLoader.enqueue(imageRequest)

        return null
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? = null
}