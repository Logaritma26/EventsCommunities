package com.log.eventscommunities.domain.model

data class Event(
    val title: String,
    val description: String,
    val tag: Int,
    val time: Long,
    val location: String,
    val organizer: Organizer,
    val attendants: List<Attendee> = listOf(),
)

data class Organizer(
    val organizer: String,
    val organizerName: String,
)

data class Attendee(
    val organizer: String,
    val organizerName: String,
)
