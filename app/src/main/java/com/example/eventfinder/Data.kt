package com.example.eventfinder

data class EventResponse(
    val _embedded: EmbeddedEvents
)

data class EmbeddedEvents(
    val events: List<Event>?
)

data class Event(
    val name: String?,
    val id: String?,
    val url: String?,
    val dates: Dates?,
    val classifications: List<Classification>?,
    val promoter: Promoter?,
    val _embedded: EmbeddedVenues?
)

data class Dates(
    val start: Start?
)

data class Start(
    val localDate: String?,
    val localTime: String?
)

data class Classification(
    val segment: Segment?
)

data class Segment(
    val name: String?
)

data class Promoter(
    val name: String?
)

data class EmbeddedVenues(
    val venues: List<Venue>?
)

data class Venue(
    val name: String?
)
