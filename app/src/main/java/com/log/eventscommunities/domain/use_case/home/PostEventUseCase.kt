package com.log.eventscommunities.domain.use_case.home

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.repository.HomeRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject


class PostEventUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(event: Event): Flow<Resource<Any>> {
        val data = hashMapOf(
            "title" to event.title,
            "description" to event.description,
            "location" to event.location,
            "tag" to event.tag,
            "time" to event.time,
            "organizer" to event.organizer.organizer,
            "organizerName" to event.organizer.organizerName,
        )
        Timber.d("asdf organizer name: ${event.organizer.organizerName}")
        return repository.postEvent(data = data)
    }
}