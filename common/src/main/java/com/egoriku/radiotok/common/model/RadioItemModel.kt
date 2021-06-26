package com.egoriku.radiotok.common.model

import com.egoriku.radiotok.common.EMPTY

data class RadioItemModel(
    val id: String = EMPTY,
    val streamUrl: String = EMPTY,
    val name: String = EMPTY,
    val icon: String = EMPTY,
    val hls: Long = 0L,
    val metadata: String = EMPTY
)