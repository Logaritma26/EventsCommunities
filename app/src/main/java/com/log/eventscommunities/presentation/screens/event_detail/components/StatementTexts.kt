package com.log.eventscommunities.presentation.screens.event_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.log.eventscommunities.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ColumnScope.StatementTexts(
    event: Event,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1.0F)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DescText(
            text = event.title,
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.height(36.dp))
        DescText(
            text = event.description,
            style = MaterialTheme.typography.body2.copy(fontSize = 20.sp),
            maxLines = 100,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(36.dp))
        DescText(
            text = "organizer: ${event.organizer.organizerName}",
            style = MaterialTheme.typography.caption.copy(fontSize = 16.sp),
        )
        DescText(text = "Total attendants: ${event.attendants.size}")
        DescText(text = "Location: ${event.location}")
        DescText(text = dateText(event.time))
    }
}

@Composable
private fun DescText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body2,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = text,
        style = style,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
    )
}

private fun dateText(
    date: Long,
): String {
    val pattern = "dd MMMM yyyy - HH:mm EEEE"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    return simpleDateFormat.format(calendar.time)
}