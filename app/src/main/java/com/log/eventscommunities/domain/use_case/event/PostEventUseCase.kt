package com.log.eventscommunities.domain.use_case.event

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.FunctionResponse
import com.log.eventscommunities.domain.repository.EventRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PostEventUseCase @Inject constructor(
    private val repository: EventRepository,
) {
    suspend operator fun invoke(
        event: Event
    ): Flow<Resource<FunctionResponse>> {
        val data = hashMapOf(
            "title" to event.title,
            "description" to event.description,
            "location" to event.location,
            "tag" to event.tag,
            "time" to event.time,
            "organizer" to event.organizer.organizer,
            "organizerName" to event.organizer.organizerName,
        )
        return repository.postEvent(data = data)
    }
}