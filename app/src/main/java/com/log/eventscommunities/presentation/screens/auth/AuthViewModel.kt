package com.log.eventscommunities.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.log.eventscommunities.domain.use_case.auth.FetchUserUseCase
import com.log.eventscommunities.domain.use_case.auth.RegisterUseCase
import com.log.eventscommunities.domain.use_case.auth.SignInUseCase
import com.log.eventscommunities.presentation.screens.auth.components.LoginState
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
    private val fetchUserUseCase: FetchUserUseCase,
) : ViewModel() {

    var authState by mutableStateOf(AuthState())
        private set

    var registerState by mutableStateOf(RegisterState())
        private set

    var loginState by mutableStateOf(LoginState())
        private set

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).onEach {
                loginState = loginState.copy(loginState = it)
            }.launchIn(viewModelScope)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseCase(email, password).onEach {
                registerState = registerState.copy(registeringState = it)
            }.launchIn(viewModelScope)
        }
    }

    fun checkAuthState() {
        val user = fetchUserUseCase()
        val isAuthenticated = user != null
        authState = AuthState(isAuthenticated = isAuthenticated, isLoading = false)
    }

}