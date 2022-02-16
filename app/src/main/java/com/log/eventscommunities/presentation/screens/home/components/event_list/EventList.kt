package com.log.eventscommunities.presentation.screens.home.components.event_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.presentation.util.common_composables.LoadingWidget

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun EventList(
    paddingValues: PaddingValues,
    events: List<Event>,
    isLoading: Boolean,
    setFilter: (Int) -> Unit,
    goEventDetail: (Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                FilterBox(
                    setFilter = setFilter,
                )
            }
            itemsIndexed(events) { index, event ->
                EventCard(
                    event = event,
                    goEventDetail = { detailEvent -> goEventDetail(detailEvent) },
                )
                if (index != events.lastIndex) {
                    Divider(modifier = Modifier.padding(horizontal = 32.dp))
                }
            }
        }
        LoadingWidget(state = isLoading)
    }
}