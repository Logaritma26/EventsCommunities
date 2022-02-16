package com.log.eventscommunities.domain.use_case.event

import com.log.eventscommunities.domain.model.UserDocument
import com.log.eventscommunities.domain.repository.EventRepository
import com.log.eventscommunities.domain.wrappers.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAttendingEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(userId: String): Flow<UserDocument?> {
        return repository.getAttendingEvents(userId = userId)
            .map { res ->
                if (res.isSuccess()) {
                    res.data!!
                } else {
                    null
                }
            }
    }
}