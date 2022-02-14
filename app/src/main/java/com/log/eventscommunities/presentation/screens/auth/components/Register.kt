package com.log.eventscommunities.presentation.screens.auth.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
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
import com.log.eventscommunities.domain.wrappers.Resource.*
import com.log.eventscommunities.presentation.screens.auth.AuthViewModel
import com.log.eventscommunities.presentation.util.common_composables.text_field.QTextField
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.EmailState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.PasswordState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.RetypeState
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun RegisterWidget(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {
    val state = viewModel.registerState

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

        Text(
            text = "Register",
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.W400),
        )

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
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.retypeState)
            }
        }

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                val res = validated(
                    emailState = state.emailState,
                    passwordState = state.passwordState,
                    retypeState = state.retypeState,
                )
                if (res) {
                    viewModel.register(
                        email = state.emailState.text,
                        password = state.passwordState.text,
                    )
                }
            },
        ) {
            Text("Sign Up")
        }

        AnimatedContent(targetState = state.registeringState) { targetState ->
            when (targetState) {
                is Loading -> {
                    CircularProgressIndicator()
                    Timber.d("state: loading")
                }
                is Success<FirebaseUser> -> {
                    resetFields(
                        emailState = state.emailState,
                        passwordState = state.passwordState,
                        retypeState = state.retypeState,
                    )
                    navigateToHome()
                    Timber.d("state: success")
                }
                is Error -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 42.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = targetState.exception?.localizedMessage ?: "Error occured",
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
    retypeState: TextFieldState,
) {
    emailState.text = ""
    passwordState.text = ""
    retypeState.text = ""
}

private fun validated(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    retypeState: TextFieldState,
): Boolean {
    retypeState.retype = passwordState.text
    return emailState.validate() && passwordState.validate() && retypeState.validate()
}

data class RegisterState(
    val emailState: TextFieldState = EmailState(hint = "Email"),
    val passwordState: TextFieldState = PasswordState(hint = "Password"),
    val retypeState: TextFieldState = RetypeState(
        hint = "Retype Password",
        retype = "",
    ),
    val registeringState: Resource<FirebaseUser> = Initial(),
)

