package com.log.eventscommunities.presentation.screens.home.components.event_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.ui.theme.Shapes
import com.log.eventscommunities.ui.theme.picList
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventCard(
    event: Event
) {
    val pics = picList.values.toList()

    Row(
        modifier = Modifier.fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(0.5f),
            contentAlignment = Alignment.TopCenter,
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp),
                shape = Shapes.small,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .aspectRatio(1f)
                            .padding(16.dp),
                        elevation = 8.dp,
                        shape = Shapes.small,
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = pics[event.tag]),
                            contentDescription = "tag image",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = event.title,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = event.organizer.organizerName,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .aspectRatio(0.75f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = event.description,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 9,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = dateText(event.time),
                    style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W900),
                )
            }
        }
    }
}

private fun dateText(
    date: Long,
): String {
    val pattern = "dd MMMM yyyy\nHH:mm EEEE"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    return simpleDateFormat.format(calendar.time)
}