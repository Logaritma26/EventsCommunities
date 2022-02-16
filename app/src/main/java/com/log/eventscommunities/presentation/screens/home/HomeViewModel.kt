package com.log.eventscommunities.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.use_case.auth.FetchUserUseCase
import com.log.eventscommunities.domain.use_case.auth.ReloadAuthUseCase
import com.log.eventscommunities.domain.use_case.auth.SendVerificationUseCase
import com.log.eventscommunities.domain.use_case.auth.SignOutUseCase
import com.log.eventscommunities.domain.use_case.event.FilterEventsUseCase
import com.log.eventscommunities.domain.use_case.event.GetEventsUseCase
import com.log.eventscommunities.domain.use_case.event.PostEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postEventUseCase: PostEventUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val filterEventsUseCase: FilterEventsUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val sendVerificationUseCase: SendVerificationUseCase,
    private val reloadAuthUseCase: ReloadAuthUseCase,
) : ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    fun postEvent(event: Event) {
        viewModelScope.launch {
            postEventUseCase(event = event).onEach {
                homeState = homeState.copy(addEventState = it)
            }.launchIn(viewModelScope)
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            val user = fetchUserUseCase()
            homeState = homeState.copy(user = user)
        }
    }

    fun fetchEvents() {
        viewModelScope.launch {
            getEventsUseCase().onEach {
                homeState = homeState.copy(eventList = it)
            }.launchIn(viewModelScope)
        }
    }

    fun filterEvents() {
        val filteredList = filterEventsUseCase(
            events = homeState.eventList,
            filterType = homeState.filter.value,
        )
        homeState = homeState.copy(filteredList = filteredList)
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            fetchUser()
        }
    }

    fun sendVerification() = sendVerificationUseCase()

    fun reloadAuth() = reloadAuthUseCase()

}