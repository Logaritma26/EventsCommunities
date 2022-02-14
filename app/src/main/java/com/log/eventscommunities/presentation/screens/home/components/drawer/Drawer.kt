package com.log.eventscommunities.presentation.screens.home.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.ui.theme.*

@Composable
fun Drawer(
    user: FirebaseUser?,
    signOut: () -> Unit,
    verifyMe: () -> Unit,
    goAuth: () -> Unit,
) {
    val userIsNull = user == null
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(color = CranePrimary800),
            )
            NullUserProfile(
                userIsNull = userIsNull,
                goAuth = goAuth,
            )
            UserProfile(
                user = user,
                userIsNull = userIsNull,
                signOut = signOut,
                verifyMe = verifyMe
            )
        }
    }
}