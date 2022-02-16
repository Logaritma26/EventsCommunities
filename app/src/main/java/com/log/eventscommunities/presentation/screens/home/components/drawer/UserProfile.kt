package com.log.eventscommunities.presentation.screens.home.components.drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.ui.theme.Black
import com.log.eventscommunities.ui.theme.Creme
import com.log.eventscommunities.ui.theme.VerifiedBlue

@Composable
fun UserProfile(
    user: FirebaseUser?,
    userIsNull: Boolean,
    signOut: () -> Unit,
    verifyMe: () -> Unit,
) {
    AnimatedVisibility(visible = !userIsNull) {
        user?.let {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(color = Creme)
                ) {
                    val name = if (user.displayName.isNullOrBlank()) {
                        user.email!!
                    } else {
                        user.displayName!!
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = name,
                            color = Black,
                        )
                        if (user.isEmailVerified) {
                            Icon(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                imageVector = Icons.Rounded.Verified,
                                contentDescription = "Verified Icon",
                                tint = VerifiedBlue,
                            )
                        }
                    }

                }
                if (!user.isEmailVerified) {
                    ProfileTile(
                        text = "Verify me",
                        onClick = { verifyMe() }
                    )
                }
                ProfileTile(
                    text = "Sign Out",
                    onClick = { signOut() }
                )
            }
        }
    }
}