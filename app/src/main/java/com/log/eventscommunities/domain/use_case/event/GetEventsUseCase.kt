package com.log.eventscommunities.domain.use_case.event

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.repository.EventRepository
import com.log.eventscommunities.domain.wrappers.isSuccess
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
            .map { res ->
                if (res.isSuccess()) {
                    res.data!!.filter { event ->
                        event.time > System.currentTimeMillis()
                    }.sortedBy {
                        it.time
                    }
                } else {
                    emptyList()
                }
            }
    }
}
