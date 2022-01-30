package com.log.eventscommunities.presentation.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.log.eventscommunities.R
import com.log.eventscommunities.presentation.screens.auth.components.Login
import com.log.eventscommunities.presentation.screens.auth.components.RegisterWidget
import com.log.eventscommunities.presentation.screens.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Destination(start = true)
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
) {
    var isLoginScreen by remember { mutableStateOf(true) }

    BackHandler(!isLoginScreen) {
        isLoginScreen = !isLoginScreen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.creme))
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
                        navigateToHome = { navigator.navigate(HomeScreenDestination()) },
                        goToRegister = { isLoginScreen = false }
                    )
                } else {
                    RegisterWidget(navigateToHome = { navigator.navigate(HomeScreenDestination()) })
                }
            }
        }
    }
}