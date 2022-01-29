package com.log.eventscommunities.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.log.eventscommunities.domain.use_case.auth.RegisterUseCase
import com.log.eventscommunities.domain.use_case.auth.SignInUseCase
import com.log.eventscommunities.presentation.screens.auth.components.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val registerUseCase: RegisterUseCase,

) : ViewModel() {

    var registerState by mutableStateOf(RegisterState())
        private set

   /* fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).onEach {
                registerState = registerState.copy(registeringState = it)
            }.launchIn(viewModelScope)
        }
    }*/

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseCase(email, password).onEach {
                registerState = registerState.copy(registeringState = it)
            }.launchIn(viewModelScope)
        }
    }
}