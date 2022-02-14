package com.log.eventscommunities.presentation.screens.home.components.drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable

@Composable
fun NullUserProfile(
    userIsNull: Boolean,
    goAuth: () -> Unit,
) {
    AnimatedVisibility(visible = userIsNull) {
        ProfileTile(
            text = "Sign In",
            onClick = { goAuth() }
        )
    }
}