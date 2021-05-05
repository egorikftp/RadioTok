package com.egoriku.radiotok.radioplayer.notification.listener

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.egoriku.radiotok.radioplayer.RadioService
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.NOTIFICATION_ID
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class RadioPlayerNotificationListener(
    private val radioService: RadioService
) : PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        radioService.apply {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        radioService.apply {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext, this::class.java)
                )
                startForeground(NOTIFICATION_ID, notification)
                isForegroundService = true
            }
        }
    }
}