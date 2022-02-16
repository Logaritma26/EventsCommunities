package com.log.eventscommunities.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: String = "null id client",
    val title: String,
    val description: String,
    val tag: Int,
    val time: Long,
    val location: String,
    val organizer: Organizer,
    val attendants: List<Attendee> = listOf(),
) : Parcelable

@Parcelize
data class Organizer(
    val organizer: String,
    val organizerName: String,
) : Parcelable

@Parcelize
data class Attendee(
    val id: String,
    val name: String,
) : Parcelable
