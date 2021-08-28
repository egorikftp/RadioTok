package com.egoriku.radiotok.domain.datasource

interface IRadioDnsServer {

    suspend fun lookup(): List<String>
}