package com.log.eventscommunities.presentation.screens.home.components.add_event

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.Organizer
import com.log.eventscommunities.ui.theme.picList

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddEvent(
    isCollapsed: Boolean,
    onClickCollapse: () -> Unit,
    onSendEvent: (Event) -> Unit,
    user: FirebaseUser?,
    notAuthenticatedRedirection: () -> Unit,
) {
    var state by remember { mutableStateOf(AddEventState()) }

    PeekBox(
        onClickCollapse = { onClickCollapse() },
        isCollapsed = isCollapsed,
        user = user,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EventFields(
            titleState = state.titleState,
            descState = state.descriptionState,
            locationState = state.locationState,
        )
        DateSelector(
            selected = state.dateSelectionState,
            date = state.dateState,
            onChange = { selected, date ->
                state = state.copy(
                    dateSelectionState = selected,
                    dateState = date,
                )
            }
        )
        TagSelector(
            selectedIndex = state.picState,
            onPicSelected = { pic -> state = state.copy(picState = pic) },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                submitFunction(
                    onClickCollapse = onClickCollapse,
                    onSendEvent = {
                        onSendEvent(it)
                        state = AddEventState()
                    },
                    user = user,
                    notAuthenticatedRedirection = notAuthenticatedRedirection,
                    state = state,
                )
            },
        ) {
            Text("Share Event")
        }
    }
}

private fun submitFunction(
    onClickCollapse: () -> Unit,
    onSendEvent: (Event) -> Unit,
    user: FirebaseUser?,
    notAuthenticatedRedirection: () -> Unit,
    state: AddEventState,
) {
    if (user != null) {
        if (state.validate()) {
            val organizerName = if (user.displayName.isNullOrBlank()) {
                user.email!!
            } else {
                user.displayName!!
            }
            val event = Event(
                title = state.titleState.text,
                description = state.descriptionState.text,
                location = state.locationState.text,
                tag = picList.values.indexOf(state.picState),
                time = state.dateState,
                organizer = Organizer(
                    organizer = user.uid,
                    organizerName = organizerName
                )
            )
            onSendEvent(event)
            onClickCollapse()
        }
    } else {
        notAuthenticatedRedirection()
    }
}