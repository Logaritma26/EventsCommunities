package com.log.eventscommunities.domain.use_case.event

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.repository.EventRepository
import com.log.eventscommunities.domain.wrappers.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchEventUseCase @Inject constructor(
    private val repository: EventRepository,
) {
    suspend operator fun invoke(eventId: String): Flow<Event?> {
        return repository.watchEvent(eventId).map {
            if (it.isSuccess()) {
                it.data!!
            } else {
                null
            }
        }
    }
}