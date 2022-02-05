package com.log.eventscommunities.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val organizer: String,
    val organizerName: String,
) : Parcelable
