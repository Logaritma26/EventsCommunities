package com.log.eventscommunities.presentation.screens.auth.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Login(
    signIn: (String, String) -> Unit,
    goToRegister: () -> Unit,
    userAuthState: AuthState,
) {
    val state by remember { mutableStateOf(LoginState()) }
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
                    .clickable { goToRegister() },
                text = "or register",
                style = MaterialTheme.typography.caption,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        ) {
            Column {
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.emailState)
                QTextField(modifier = Modifier.fillMaxWidth(), state = state.passwordState)
            }
        }

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                if (state.validated()) {
                    signIn(
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

private data class LoginState(
    val emailState: TextFieldState = EmailState(hint = "Email"),
    val passwordState: TextFieldState = PasswordState(hint = "Password"),
) {
    fun resetFields() {
        emailState.text = ""
        passwordState.text = ""
    }

    fun validated(): Boolean = emailState.validate() && passwordState.validate()
}
