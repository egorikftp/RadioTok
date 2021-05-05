package com.egoriku.radiotok.data.repository

import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.data.entity.StationEntity
import com.egoriku.radiotok.domain.datasource.IRadioServerDataSource
import com.egoriku.radiotok.domain.datasource.IStationsDataSource
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository

class RadioFetchNetworkRepository(
    private val radioServerDataSource: IRadioServerDataSource,
    private val stationsDataSource: IStationsDataSource
) : IRadioFetchNetworkRepository {

    override suspend fun load(): List<RadioItemModel> {
        val loadStations = radioServerDataSource.loadRadioServices()
        val baseUrl = loadStations.random()

        return stationsDataSource.load(baseUrl = baseUrl).map {
            RadioItemModel(
                id = it.stationUuid,
                streamUrl = it.streamUrl,
                name = it.name,
                icon = it.favicon,
                hsl = it.hls.toLong(),
                metadata = buildMetadata(it)
            )
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun buildMetadata(entity: StationEntity) = buildList {
        if (entity.countryCode.isNotEmpty()) {
            add(entity.countryCode)
        }

        if (entity.tags.isNotEmpty()) {
            add(entity.tags.replace(",", ", "))
        }
    }.joinToString(" / ")
}
