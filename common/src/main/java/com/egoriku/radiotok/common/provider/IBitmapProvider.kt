package com.egoriku.radiotok.common.provider

import android.graphics.Bitmap

interface IBitmapProvider {

    val icCollection: Bitmap
    val icPersonal: Bitmap
    val icRadioWaves: Bitmap
    val icSmartPlaylist: Bitmap

    val icChangedLatelyRound: Bitmap
    val icDislikedRound: Bitmap
    val icHistoryRound: Bitmap
    val icLikedRound: Bitmap
    val icLocalRound: Bitmap
    val icPlayingRound: Bitmap
    val icShuffleRound: Bitmap
    val icTopClicksRound: Bitmap
    val icTopVoteRound: Bitmap
}