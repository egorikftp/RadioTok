package com.egoriku.radiotok.exoplayer.notification.listener

import android.content.Intent
import android.view.KeyEvent
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class NotificationMediaButtonEventHandler(
    private val onNext: () -> Unit
) : MediaSessionConnector.MediaButtonEventHandler {

    override fun onMediaButtonEvent(
        player: Player,
        controlDispatcher: ControlDispatcher,
        mediaButtonEvent: Intent
    ): Boolean {
        var canHandle = false

        val extras = mediaButtonEvent.extras ?: return canHandle

        if (extras.containsKey(Intent.EXTRA_KEY_EVENT)) {
            val keyEvent = extras.getParcelable<KeyEvent>(Intent.EXTRA_KEY_EVENT)

            if (keyEvent?.action == KeyEvent.ACTION_DOWN) {
                when (keyEvent.keyCode) {
                    KeyEvent.KEYCODE_MEDIA_NEXT -> {
                        onNext()
                        canHandle = true
                    }
                }
            }
        }
        return canHandle
    }
}