package com.egoriku.radiotok.common.provider

import android.graphics.Bitmap
import android.net.Uri

interface IBitmapProvider {

    val icCollection: Bitmap
    val icPersonal: Bitmap
    val icRadioWaves: Bitmap
    val icSmartPlaylist: Bitmap

    val icChangedLatelyRound: Bitmap
    val icCountryRounded: Bitmap
    val icDislikedRound: Bitmap
    val icTagsRound: Bitmap
    val icHistoryRound: Bitmap
    val icLanguageRound: Bitmap
    val icLikedRound: Bitmap
    val icLocalRound: Bitmap
    val icPlayingRound: Bitmap
    val icShuffleRound: Bitmap
    val icTopClicksRound: Bitmap
    val icTopVoteRound: Bitmap

    val bgRadioGradient: Uri
}