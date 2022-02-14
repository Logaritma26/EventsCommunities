package com.log.eventscommunities.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.use_case.auth.FetchUserUseCase
import com.log.eventscommunities.domain.use_case.auth.RegisterUseCase
import com.log.eventscommunities.domain.use_case.auth.SignInUseCase
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.domain.wrappers.Resource.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val registerUseCase: RegisterUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
) : ViewModel() {

    var authState by mutableStateOf(AuthState())
        private set

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).onEach {
                updateUserState(it)
            }.launchIn(viewModelScope)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseCase(email, password).onEach {
                updateUserState(it)
            }.launchIn(viewModelScope)
        }
    }

    private fun updateUserState(user: Resource<FirebaseUser>) {
        Timber.d("auth state: ", user.toString())
        when (user) {
            is Success -> {
                Timber.d("state loading called")
                authState = authState.copy(
                    userState = user.data,
                    isLoading = false,
                    isError = false,
                    errorMessage = null,
                )
            }
            is Error -> {
                Timber.d("state error called", user.exception?.localizedMessage ?: "error asdf")
                authState = authState.copy(
                    isError = true,
                    errorMessage = user.exception?.localizedMessage,
                    isLoading = false,
                )
            }
            is Loading -> {
                Timber.d("state loading called")
                authState = authState.copy(
                    isLoading = true,
                    isError = false,
                    errorMessage = null,
                )
            }
            else -> {

            }
        }
    }

    fun checkAuthState() {
        val user = fetchUserUseCase()
        authState = AuthState(
            userState = user,
            isLoading = false,
        )
    }
}