package com.log.eventscommunities.presentation.screens.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.presentation.screens.auth.AuthViewModel
import com.log.eventscommunities.presentation.util.common_composables.text_field.QTextField
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.EmailState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.PasswordState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.RetypeState
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun RegisterWidget(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state = viewModel.registerState

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp,
                    vertical = 12.dp,
                ),
        ) {
            Column {
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.emailState)
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.passwordState)
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.retypeState)
            }

        }

        Button(
            onClick = {
                val res = validated(
                    emailState = state.emailState,
                    passwordState = state.passwordState,
                    retypeState = state.retypeState,
                )
                Timber.d("res: $res")
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
    }
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
    val registeringState: Resource<FirebaseUser> = Resource.Initial(),
)

