package com.egoriku.radiotok.domain.model

import com.egoriku.radiotok.extensions.EMPTY

data class RadioItemModel(
    val id: String = EMPTY,
    val streamUrl: String = EMPTY,
    val name: String = EMPTY,
    val icon: String = EMPTY,
    val isHsl: Boolean = false
)