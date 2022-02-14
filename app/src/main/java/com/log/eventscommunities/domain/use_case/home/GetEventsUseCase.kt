package com.log.eventscommunities.domain.use_case.home

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.repository.HomeRepository
import com.log.eventscommunities.domain.wrappers.Resource
import com.log.eventscommunities.domain.wrappers.isSuccess
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
            .map { res ->
                if (res.isSuccess()) {
                    res.data!!.filter { event ->
                        event.time > System.currentTimeMillis()
                    }
                } else {
                    emptyList()
                }
            }
    }
}
