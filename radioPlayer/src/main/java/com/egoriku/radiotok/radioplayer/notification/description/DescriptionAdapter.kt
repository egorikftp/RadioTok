package com.egoriku.radiotok.radioplayer.notification.description

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.media.session.MediaControllerCompat
import com.bumptech.glide.Glide
import com.egoriku.radiotok.radioplayer.R
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.coroutines.*

const val NOTIFICATION_LARGE_ICON_SIZE = 144

class DescriptionAdapter(
    private val context: Context,
    private val mediaController: MediaControllerCompat,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder
) : PlayerNotificationManager.MediaDescriptionAdapter {

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private var currentIconUri: Uri? = null
    private var currentBitmap: Bitmap? = null

    override fun getCurrentContentTitle(player: Player) =
        getDescription(index = player.currentMediaItemIndex)?.title.toString()

    override fun createCurrentContentIntent(player: Player): PendingIntent? =
        mediaController.sessionActivity

    override fun getCurrentContentText(player: Player) =
        getDescription(index = player.currentMediaItemIndex)?.subtitle.toString()

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val iconUri = getDescription(index = player.currentMediaItemIndex)?.iconUri

        return if (currentIconUri != iconUri || currentBitmap == null) {
            currentBitmap = null
            currentIconUri = iconUri

            serviceScope.launch {
                currentBitmap = loadBitmap(iconUri)
                currentBitmap?.let { callback.onBitmap(it) }
            }

            null
        } else {
            currentBitmap
        }
    }

    private fun getDescription(index: Int) =
        currentRadioQueueHolder.getMediaMetadataOrNull(index)?.description

    private suspend fun loadBitmap(iconUri: Uri?): Bitmap? = withContext(Dispatchers.IO) {
        runCatching {
            Glide.with(context)
                .asBitmap()
                .load(iconUri)
                .submit(NOTIFICATION_LARGE_ICON_SIZE, NOTIFICATION_LARGE_ICON_SIZE)
                .get()
        }.getOrDefault(
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_radio_round)
        )
    }
}