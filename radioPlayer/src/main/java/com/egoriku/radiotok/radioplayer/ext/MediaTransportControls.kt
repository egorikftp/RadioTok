@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.radiotok.radioplayer.ext

import android.support.v4.media.session.MediaControllerCompat.TransportControls
import androidx.core.os.bundleOf
import com.egoriku.radiotok.radioplayer.constant.CustomAction.ACTION_DISLIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.ACTION_TOGGLE_FAVORITE

inline fun TransportControls.sendDislikeAction() {
    sendCustomAction(ACTION_DISLIKE, bundleOf())
}

inline fun TransportControls.sendLikeAction() {
    sendCustomAction(ACTION_TOGGLE_FAVORITE, bundleOf())
}