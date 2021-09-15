package com.egoriku.radiotok.radioplayer.listener

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventHandler {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    suspend fun playNextRandom() {
        _event.emit(Event.PlayNextRandom)
    }

    sealed class Event {
        object PlayNextRandom : Event()
    }
}