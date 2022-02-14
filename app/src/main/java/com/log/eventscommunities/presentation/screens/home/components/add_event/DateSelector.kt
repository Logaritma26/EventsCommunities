package com.log.eventscommunities.presentation.screens.home.components.add_event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateSelector(
    selected: MutableState<Boolean>,
    date: MutableState<Long>,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = dateText(
                selected = selected.value,
                date = date.value,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            openDialog(
                context = context,
                date = date,
                dateSelectionState = selected,
            )
        }) {
            Text(text = "Select Date")
        }
    }
}

private fun dateText(selected: Boolean, date: Long): String {
    return if (selected) {
        val pattern = "dd MMMM yyyy\nHH:mm EEEE"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        simpleDateFormat.format(calendar.time)
    } else {
        "You didn't selected a date yet!"
    }
}

private fun openDialog(
    context: Context,
    date: MutableState<Long>,
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
                    dateSelectionState.value = true
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day, hour, minute)
                    date.value = calendar.timeInMillis
                },
                startHour, startMinute, false,
            ).show()
        },
        startYear, startMonth, startDay,
    ).show()
}