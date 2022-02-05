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
            "date" to event.date,
            "location" to event.location,
            "organizer" to event.organizer,
            "organizerName" to event.organizerName,
        )
        Timber.d("asdf organizer name: ${event.organizerName}")
        return repository.postEvent(data = data)
    }
}