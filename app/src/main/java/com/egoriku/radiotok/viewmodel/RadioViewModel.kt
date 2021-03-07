package com.egoriku.radiotok.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.radiotok.data.datasource.IRadioDataSource
import com.egoriku.radiotok.data.datasource.IStationsDataSource
import com.egoriku.radiotok.data.entity.StationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RadioViewModel(
    private val radioDataSource: IRadioDataSource,
    private val stationsDataSource: IStationsDataSource
) : ViewModel() {

    private val _stations = MutableStateFlow(emptyList<StationEntity>())
    val stations: StateFlow<List<StationEntity>> = _stations

    init {
        viewModelScope.launch {
            val loadStations = radioDataSource.loadRadioServices()

            val baseUrl = loadStations.random()

            val radioStation = stationsDataSource.load(baseUrl = baseUrl)

            _stations.value = radioStation
        }
    }
}