package com.log.eventscommunities.presentation.screens.home.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.presentation.util.common_composables.text_field.TextFieldState
import com.log.eventscommunities.presentation.util.common_composables.text_field.WTextField
import java.util.*

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
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "Add New Event",
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { onClickCollapse() },
        ) {
            AnimatedContent(targetState = isCollapsed) { targetState ->
                if (targetState) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "Open Bottom Sheet",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Close Bottom Sheet",
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .scrollable(
                rememberScrollState(),
                orientation = Orientation.Vertical,
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WTextField(modifier = Modifier.fillMaxWidth(), state = state.titleState)
        Spacer(modifier = Modifier.height(24.dp))
        WTextField(
            modifier = Modifier.fillMaxWidth(),
            state = state.descriptionState,
            maxLines = 4,
        )
        Spacer(modifier = Modifier.height(24.dp))
        WTextField(modifier = Modifier.fillMaxWidth(), state = state.locationState)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = state.dateState.value)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            openDialog(
                context = context,
                date = state.dateState,
                dateSelectionState = state.dateSelectionState,
            )
        }) {
            Text("Select Date")
        }

        Button(
            onClick = {
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
                            date = state.dateState.value,
                            organizer = user.uid,
                            organizerName = organizerName
                        )
                        onSendEvent(event)
                        state.reset()
                        onClickCollapse()
                    }
                } else {
                    notAuthenticatedRedirection()
                }

            }
        ) {
            Text("Share Event")
        }
    }
}

private fun openDialog(
    context: Context,
    date: MutableState<String>,
    dateSelectionState: MutableState<Boolean>,
) {
    val currentDateTime = Calendar.getInstance()
    val startYear = currentDateTime.get(Calendar.YEAR)
    val startMonth = currentDateTime.get(Calendar.MONTH)
    val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
    val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    val startMinute = currentDateTime.get(Calendar.MINUTE)

    DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    val monthStr: String = if ((month + 1).toString().length == 1) {
                        "0${month + 1}"
                    } else {
                        month.toString()
                    }
                    date.value = "$day - $monthStr - $year / $hour:$minute"
                    dateSelectionState.value = true
                },
                startHour, startMinute, false,
            ).show()
        },
        startYear, startMonth, startDay,
    ).show()
}

class AddEventState(
    val titleState: TextFieldState,
    val descriptionState: TextFieldState,
    val locationState: TextFieldState,
    val dateState: MutableState<String>,
    val dateSelectionState: MutableState<Boolean>,
) {
    fun validate(): Boolean {
        return titleState.validate() && descriptionState.validate() && locationState.validate() && dateSelectionState.value
    }

    fun reset() {
        titleState.text = ""
        descriptionState.text = ""
        locationState.text = ""
    }
}

@Composable
fun rememberAddEventState(
    titleState: TextFieldState = remember { TextFieldState(pHint = "Title") },
    descriptionState: TextFieldState = remember { TextFieldState(pHint = "Description") },
    locationState: TextFieldState = remember { TextFieldState(pHint = "Location") },
    dateState: MutableState<String> = remember { mutableStateOf("You didn't select a date yet!") },
    dateSelectionState: MutableState<Boolean> = remember { mutableStateOf(false) },
) = remember(titleState, descriptionState, locationState, dateState, dateSelectionState) {
    AddEventState(titleState, descriptionState, locationState, dateState, dateSelectionState)
}


















