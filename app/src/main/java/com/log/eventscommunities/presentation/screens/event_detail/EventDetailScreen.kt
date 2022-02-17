package com.log.eventscommunities.presentation.screens.event_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.presentation.screens.event_detail.components.StatementTexts
import com.log.eventscommunities.presentation.util.common_composables.LoadingWidget
import com.log.eventscommunities.ui.theme.Shapes
import com.log.eventscommunities.ui.theme.White
import com.log.eventscommunities.ui.theme.picList
import com.ramcosta.composedestinations.annotation.Destination

@Destination(start = false)
@Composable
fun EventDetail(
    event: Event,
    viewModel: EventDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.init(event = event)
    }

    val state = viewModel.eventDetailState
    val isAttended = state.isAttended(eventId = event.id)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
                    .padding(32.dp),
                elevation = 8.dp,
                shape = Shapes.small,
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = picList.values.toList()[event.tag]),
                    contentDescription = "Tag Image",
                    contentScale = ContentScale.Crop,
                )
            }
            StatementTexts(event = state.event ?: event)
            Button(
                modifier = Modifier.padding(36.dp),
                onClick = {
                    state.user?.let {
                        if (it.isEmailVerified) {
                            viewModel.manageEvent(
                                eventId = event.id,
                                isAttending = !isAttended,
                            )
                        }
                    }
                },
            ) {
                Text(
                    text = getText(
                        user = state.user,
                        isAttending = state.isAttended(eventId = event.id)
                    ),
                )
            }
        }
        LoadingWidget(state = state.isLoading())
    }
}

private fun getText(
    user: FirebaseUser?,
    isAttending: Boolean,
): String {
    return when {
        user == null -> "Please sign in to attend"
        isAttending -> "Leave event"
        user.isEmailVerified -> "Attend"
        else -> "Please verify your email first"
    }
}