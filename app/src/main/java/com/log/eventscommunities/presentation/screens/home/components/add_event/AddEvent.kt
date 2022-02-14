package com.log.eventscommunities.presentation.screens.home.components.add_event

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.Organizer
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
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
    val state = rememberAddEventState()

    PeekBox(
        onClickCollapse = { onClickCollapse() },
        isCollapsed = isCollapsed,
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
        DateSelector(selected = state.dateSelectionState, date = state.dateState)
        TagSelector(
            selectedIndex = state.picState.value,
            onPicSelected = { pic -> state.picState.value = pic },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                submitFunction(
                    onClickCollapse = onClickCollapse,
                    onSendEvent = onSendEvent,
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
                tag = picList.values.indexOf(state.picState.value),
                time = state.dateState.value,
                organizer = Organizer(
                    organizer = user.uid,
                    organizerName = organizerName
                )
            )
            onSendEvent(event)
            state.reset()
            onClickCollapse()
        }
    } else {
        notAuthenticatedRedirection()
    }
}

// TODO convert to separated data class state
class AddEventState(
    val titleState: TextFieldState,
    val descriptionState: TextFieldState,
    val locationState: TextFieldState,
    val dateState: MutableState<Long>,
    val dateSelectionState: MutableState<Boolean>,
    val picState: MutableState<Int>,
) {
    fun validate(): Boolean {
        return titleState.validate() && descriptionState.validate() && locationState.validate() && dateSelectionState.value
    }

    fun reset() {
        titleState.text = ""
        descriptionState.text = ""
        locationState.text = ""
        dateState.value = 0L
        picState.value = 1
    }
}

@Composable
private fun rememberAddEventState(
    titleState: TextFieldState = remember { TextFieldState(pHint = "Title") },
    descriptionState: TextFieldState = remember { TextFieldState(pHint = "Description") },
    locationState: TextFieldState = remember { TextFieldState(pHint = "Location") },
    dateState: MutableState<Long> = remember { mutableStateOf(0L) },
    dateSelectionState: MutableState<Boolean> = remember { mutableStateOf(false) },
    picState: MutableState<Int> = remember { mutableStateOf(1) },
) = remember(titleState, descriptionState, locationState, dateState, dateSelectionState, picState) {
    AddEventState(
        titleState,
        descriptionState,
        locationState,
        dateState,
        dateSelectionState,
        picState,
    )
}