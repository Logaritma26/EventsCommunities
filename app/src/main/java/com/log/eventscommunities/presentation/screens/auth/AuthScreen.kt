package com.log.eventscommunities.presentation.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.log.eventscommunities.domain.repository.AuthRepository
import com.log.eventscommunities.presentation.screens.auth.components.RegisterWidget
import com.log.eventscommunities.presentation.util.common_composables.text_field.QTextField
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Destination(start = true)
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = hiltViewModel(),
) {
  /*  val state by remember { mutableStateOf(false) }

    AnimatedContent(targetState = state) {

    }*/
    RegisterWidget()

}
