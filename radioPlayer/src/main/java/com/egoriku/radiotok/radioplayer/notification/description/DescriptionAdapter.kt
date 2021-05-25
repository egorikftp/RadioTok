package com.egoriku.radiotok.radioplayer.notification.description

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaControllerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.R
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class DescriptionAdapter(
    private val context: Context,
    private val mediaController: MediaControllerCompat,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder
) : PlayerNotificationManager.MediaDescriptionAdapter {

    override fun getCurrentContentTitle(player: Player) =
        getDescription().title.toString()

    override fun createCurrentContentIntent(player: Player): PendingIntent? =
        mediaController.sessionActivity

    override fun getCurrentContentText(player: Player) =
        getDescription().subtitle.toString()

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        logD("getCurrentLargeIcon: ${getDescription().iconUri}")

        Glide.with(context)
            .asBitmap()
            .placeholder(R.drawable.ic_radio_white)
            .load(getDescription().iconUri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        return null
    }

    private fun getDescription(): MediaDescriptionCompat {
        val currentMediaMetadata = requireNotNull(currentRadioQueueHolder.currentMediaMetadata)

        return currentMediaMetadata.description
    }
}