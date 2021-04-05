package com.egoriku.radiotok.domain.datasource

interface IRadioServerDataSource {

    suspend fun loadRadioServices(): List<String>
}