package com.egoriku.radiotok.model

data class PlayerModel(
    val isHsl: Boolean = false,
    val streamUrl: String = EMPTY,
    val name: String = EMPTY,
    val icon: String = EMPTY
)

const val EMPTY = ""