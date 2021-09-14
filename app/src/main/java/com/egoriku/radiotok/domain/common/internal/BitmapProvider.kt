package com.egoriku.radiotok.domain.common.internal

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.ext.drawableCompat
import com.egoriku.radiotok.common.ext.getIconUri
import com.egoriku.radiotok.common.provider.IBitmapProvider
import com.egoriku.radiotok.extension.roundWithBorder

internal class BitmapProvider(private val context: Context) : IBitmapProvider {

    override val icCollection: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_collection).toBitmap()

    override val icPersonal: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_personal).toBitmap()

    override val icRadioWaves: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_radio_waves).toBitmap()

    override val icSmartPlaylist: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_smart_playlist).toBitmap()

    override val icChangedLatelyRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_changed_lately)
            .toBitmap()
            .roundWithBorder()

    override val icCountryRounded: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_country)
            .toBitmap()
            .roundWithBorder()

    override val icDislikedRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_not_interested)
            .toBitmap()
            .roundWithBorder()

    override val icHistoryRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_history)
            .toBitmap()
            .roundWithBorder()

    override val icLanguageRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_language)
            .toBitmap()
            .roundWithBorder()

    override val icLikedRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_liked)
            .toBitmap()
            .roundWithBorder()

    override val icLocalRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_local)
            .toBitmap()
            .roundWithBorder()

    override val icPlayingRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_playing)
            .toBitmap()
            .roundWithBorder()

    override val icShuffleRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_shuffle)
            .toBitmap()
            .roundWithBorder()

    override val icTagsRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_genres)
            .toBitmap()
            .roundWithBorder()

    override val icTopClicksRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_top_clicks)
            .toBitmap()
            .roundWithBorder()

    override val icTopVoteRound: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_top_vote)
            .toBitmap()
            .roundWithBorder()

    override val bgRadioGradient: Uri
        get() = context.getIconUri(R.drawable.bg_radio_gradient)
}