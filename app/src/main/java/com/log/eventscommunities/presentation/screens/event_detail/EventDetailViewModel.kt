package com.log.eventscommunities.presentation.screens.event_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.use_case.auth.FetchUserUseCase
import com.log.eventscommunities.domain.use_case.event.ManageEventUseCase
import com.log.eventscommunities.domain.use_case.event.GetAttendingEventsUseCase
import com.log.eventscommunities.domain.use_case.event.WatchEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val attendEventUseCase: ManageEventUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val getAttendingEventsUseCase: GetAttendingEventsUseCase,
    private val watchEventUseCase: WatchEventUseCase,
) : ViewModel() {

    var eventDetailState by mutableStateOf(EventDetailState())
        private set

    fun init(event: Event) {
        fetchUser()
        watchEvent(eventId = event.id)
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val user = fetchUserUseCase()
            eventDetailState = eventDetailState.copy(user = user)
            user?.let {
                getAttendingEvents(it.uid)
            }
        }
    }

    fun manageEvent(
        eventId: String,
        isAttending: Boolean,
    ) {
        val data = getEventData(eventId = eventId)
        viewModelScope.launch {
            attendEventUseCase(
                data = data,
                isAttending = isAttending,
            ).onEach {
                eventDetailState = eventDetailState.copy(attendEventState = it)
            }.launchIn(viewModelScope)
        }
    }

    private fun watchEvent(eventId: String) {
        viewModelScope.launch {
            watchEventUseCase(eventId).onEach {
                eventDetailState = eventDetailState.copy(event = it)
            }.launchIn(viewModelScope)
        }

    }

    private fun getEventData(eventId: String): HashMap<String, String> {
        val user = eventDetailState.user!!
        val name = if (user.displayName.isNullOrBlank()) {
            user.email!!
        } else {
            user.displayName!!
        }
        return hashMapOf(
            "id" to user.uid,
            "eventId" to eventId,
            "name" to name,
        )
    }

    private fun getAttendingEvents(userId: String) {
        viewModelScope.launch {
            getAttendingEventsUseCase(userId).onEach {
                eventDetailState = eventDetailState.copy(userDocument = it)
            }.launchIn(viewModelScope)
        }
    }

}