package com.log.eventscommunities.presentation.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.R
import com.log.eventscommunities.presentation.screens.auth.components.Login
import com.log.eventscommunities.presentation.screens.auth.components.Register
import com.log.eventscommunities.presentation.screens.destinations.HomeScreenDestination
import com.log.eventscommunities.presentation.util.common_composables.LoadingWidget
import com.log.eventscommunities.ui.theme.Creme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Destination
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var isLoginScreen by remember { mutableStateOf(true) }
    val state = viewModel.authState

    LaunchedEffect(key1 = true) {
        viewModel.checkAuthState()
    }

    if (state.isAuthenticated()) {
        navigator.navigate(HomeScreenDestination())
    }
    BackHandler(!isLoginScreen) {
        isLoginScreen = !isLoginScreen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Creme)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.ic_auth_screen_splash),
            contentDescription = "splash image",
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedContent(
                targetState = isLoginScreen,
                transitionSpec = {
                    if (targetState) {
                        slideInHorizontally { height -> -height } + fadeIn() with
                                slideOutHorizontally { height -> height } + fadeOut()
                    } else {
                        slideInHorizontally { height -> height } + fadeIn() with
                                slideOutHorizontally { height -> -height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) { targetState ->
                if (targetState) {
                    Login(
                        signIn = { email, passwd ->
                            viewModel.signIn(
                                email = email,
                                password = passwd,
                            )
                        },
                        goToRegister = { isLoginScreen = false },
                        userAuthState = state,
                    )
                } else {
                    Register(
                        userAuthState = state,
                        register = { email, passwd ->
                            viewModel.register(
                                email = email,
                                password = passwd,
                            )
                        }
                    )
                }
            }
        }
        LoadingWidget(state = state.isLoading)
    }
}

data class AuthState(
    val isLoading: Boolean = true,
    val userState: FirebaseUser? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null,
) {
    fun isAuthenticated(): Boolean = userState != null
}