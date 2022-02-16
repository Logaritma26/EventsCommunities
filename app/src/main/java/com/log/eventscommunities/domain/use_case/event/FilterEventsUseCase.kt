package com.log.eventscommunities.domain.use_case.event

import com.log.eventscommunities.domain.model.Event
import javax.inject.Inject

class FilterEventsUseCase @Inject constructor() {
    operator fun invoke(
        events: List<Event>,
        filterType: Int,
    ): List<Event> {
        return if (filterType == -1) {
            events
        } else {
            events.filter {
                it.tag == filterType
            }
        }
    }
}