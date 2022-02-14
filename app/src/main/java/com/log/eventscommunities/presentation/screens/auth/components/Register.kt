package com.log.eventscommunities.presentation.screens.auth.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.presentation.screens.auth.AuthState
import com.log.eventscommunities.presentation.util.common_composables.text_field.QTextField
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.EmailState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.PasswordState
import com.log.eventscommunities.presentation.util.common_composables.text_field.text_field_states.RetypeState

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Register(
    register: (String, String) -> Unit,
    userAuthState: AuthState,
) {
    val state by remember { mutableStateOf(RegisterState()) }
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
                if (state.validated()) {
                    register(
                        state.emailState.text,
                        state.passwordState.text,
                    )
                }
            },
        ) {
            Text("Sign Up")
        }

        AnimatedVisibility(visible = userAuthState.isError) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                elevation = 6.dp,
            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = userAuthState.errorMessage ?: "Error occured",
                )
            }
        }

        if (userAuthState.isAuthenticated()) {
            state.resetFields()
        }
    }
}

private data class RegisterState(
    val emailState: TextFieldState = EmailState(hint = "Email"),
    val passwordState: TextFieldState = PasswordState(hint = "Password"),
    val retypeState: TextFieldState = RetypeState(
        hint = "Retype Password",
        retype = "",
    ),
) {
    fun validated(): Boolean {
        retypeState.retype = passwordState.text
        return emailState.validate() && passwordState.validate() && retypeState.validate()
    }

    fun resetFields() {
        emailState.text = ""
        passwordState.text = ""
        retypeState.text = ""
    }
}

