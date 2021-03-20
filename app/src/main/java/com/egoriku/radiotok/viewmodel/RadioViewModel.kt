package com.egoriku.radiotok.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.radiotok.data.datasource.IRadioDataSource
import com.egoriku.radiotok.data.datasource.IStationsDataSource
import com.egoriku.radiotok.data.entity.StationEntity
import com.egoriku.radiotok.model.PlayerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RadioViewModel(
    private val radioDataSource: IRadioDataSource,
    private val stationsDataSource: IStationsDataSource
) : ViewModel() {

    private val _stations = MutableStateFlow(emptyList<StationEntity>())

    private val _playerModel = MutableStateFlow(PlayerModel())
    val currentPlayerModel: StateFlow<PlayerModel> = _playerModel

    init {
        viewModelScope.launch {
            val loadStations = radioDataSource.loadRadioServices()

            val baseUrl = loadStations.random()

            val radioStation = stationsDataSource.load(baseUrl = baseUrl)
            _stations.value = radioStation

            nextStation()
        }
    }

    fun nextStation() {
        val stations = _stations.value

        if (stations.isEmpty()) {
            Log.d("kek", "stations is empty")

            return
        }

        viewModelScope.launch {
            val random = stations.random()

            Log.d("kek", random.toString())

            _playerModel.value = PlayerModel(
                isHsl = random.hls == 1,
                streamUrl = random.streamUrl,
                name = random.name,
                icon = random.favicon
            )
        }
    }
}