package com.log.eventscommunities.presentation.screens.auth.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.presentation.screens.auth.AuthViewModel
import com.log.eventscommunities.presentation.util.common_composables.text_field.QTextField
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.EmailState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.PasswordState
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Login(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    goToRegister: () -> Unit,
) {
    val state = viewModel.loginState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topEnd = 36.dp,
                    topStart = 36.dp,
                )
            )
            .background(color = Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.W400),
            )
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .clickable {
                        goToRegister()
                    },
                text = "or register",
                style = MaterialTheme.typography.caption,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp,
                ),
        ) {
            Column {
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.emailState)
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.passwordState)
            }
        }

        Button(
            onClick = {
                val res = validated(
                    emailState = state.emailState,
                    passwordState = state.passwordState,
                )
                if (res) {
                    viewModel.signIn(
                        email = state.emailState.text,
                        password = state.passwordState.text,
                    )
                }
            },
        ) {
            Text("Sign Up")
        }

        AnimatedContent(targetState = state.loginState) { targetState ->
            when (targetState) {
                is Resource.Loading<FirebaseUser> -> {
                    CircularProgressIndicator()
                    Timber.d("state: loading")
                }
                is Resource.Success<FirebaseUser> -> {
                    resetFields(
                        emailState = state.emailState,
                        passwordState = state.passwordState,
                    )
                    navigateToHome()
                    Timber.d("state: success")
                }
                is Resource.Error<FirebaseUser> -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 42.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = targetState.message!!,
                        )
                    }
                    Timber.d("state: error")
                }
                else -> {
                    Timber.d("state: else")
                }
            }
        }
    }
}

private fun resetFields(
    emailState: TextFieldState,
    passwordState: TextFieldState,
) {
    emailState.text = ""
    passwordState.text = ""
}

private fun validated(
    emailState: TextFieldState,
    passwordState: TextFieldState,
): Boolean = emailState.validate() && passwordState.validate()

data class LoginState(
    val emailState: TextFieldState = EmailState(hint = "Email"),
    val passwordState: TextFieldState = PasswordState(hint = "Password"),
    val loginState: Resource<FirebaseUser> = Resource.Initial(),
)
