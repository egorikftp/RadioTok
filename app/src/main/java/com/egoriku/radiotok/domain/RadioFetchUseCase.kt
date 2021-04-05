package com.egoriku.radiotok.domain

import com.egoriku.radiotok.domain.datasource.IRadioServerDataSource
import com.egoriku.radiotok.domain.datasource.IStationsDataSource
import com.egoriku.radiotok.domain.model.RadioItemModel

internal class RadioFetchUseCase(
    private val radioServerDataSource: IRadioServerDataSource,
    private val stationsDataSource: IStationsDataSource
) : IRadioFetchUseCase {

    private var _radios = emptyList<RadioItemModel>()

    override val radios: List<RadioItemModel>
        get() = _radios

    override suspend fun load(): List<RadioItemModel> {
        val loadStations = radioServerDataSource.loadRadioServices()
        val baseUrl = loadStations.random()

        _radios = stationsDataSource.load(baseUrl = baseUrl).map {
            RadioItemModel(
                id = it.stationUuid,
                streamUrl = it.streamUrl,
                name = it.name,
                icon = it.favicon,
                isHsl = it.hls == 1
            )
        }

        _radios = _radios.shuffled()

        return _radios
    }
}

interface IRadioFetchUseCase {

    val radios: List<RadioItemModel>

    suspend fun load(): List<RadioItemModel>
}