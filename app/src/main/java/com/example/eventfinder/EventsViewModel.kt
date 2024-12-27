package com.example.eventfinder

import androidx.lifecycle.ViewModel

class EventsViewModel : ViewModel() {
    var events: List<Event> = emptyList()

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
    }
}
