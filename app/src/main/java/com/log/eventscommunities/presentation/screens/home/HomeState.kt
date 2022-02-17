package com.log.eventscommunities.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.FunctionResponse
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.domain.wrappers.isLoading

data class HomeState(
    val addEventState: Resource<FunctionResponse> = Resource.Initial(),
    val user: FirebaseUser? = null,
    val eventList: List<Event> = emptyList(),
    val filteredList: List<Event> = emptyList(),
    val filter: MutableState<Int> = mutableStateOf(-1),
) {
    fun isLoading(): Boolean = addEventState.isLoading()
}