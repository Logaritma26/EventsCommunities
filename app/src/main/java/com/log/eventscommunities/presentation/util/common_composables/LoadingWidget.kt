package com.log.eventscommunities.presentation.util.common_composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.LoadingWidget(
    state: Boolean
) {
    AnimatedVisibility(
        modifier = Modifier.align(Alignment.Center),
        visible = state,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1.30f),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Please wait...",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}