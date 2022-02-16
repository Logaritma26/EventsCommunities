package com.log.eventscommunities.presentation.screens.home.components.add_event

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.presentation.util.common_composables.OCIconButton

@ExperimentalAnimationApi
@Composable
fun PeekBox(
    onClickCollapse: () -> Unit,
    isCollapsed: Boolean,
    user: FirebaseUser?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = getText(user),
        )
        OCIconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            state = isCollapsed,
            onClick = onClickCollapse
        )
    }
}

private fun getText(
    user: FirebaseUser?
): String {
    return when {
        user == null -> {
            "Log in to create event!"
        }
        !user.isEmailVerified -> {
            "Verify your account to create event!"
        }
        else -> {
            "Add New Event"
        }
    }
}