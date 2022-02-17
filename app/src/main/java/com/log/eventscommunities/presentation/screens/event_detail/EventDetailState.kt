package com.log.eventscommunities.presentation.screens.event_detail

import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.FunctionResponse
import com.log.eventscommunities.domain.model.UserDocument
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.domain.wrappers.isLoading

data class EventDetailState(
    val user: FirebaseUser? = null,
    val attendEventState: Resource<FunctionResponse> = Resource.Initial(),
    val userDocument: UserDocument? = null,
    val event: Event? = null,
) {
    fun isAttended(eventId: String): Boolean {
        userDocument?.let {
            return it.attending.contains(eventId)
        }
        return false
    }

    fun isLoading(): Boolean = attendEventState.isLoading()
}