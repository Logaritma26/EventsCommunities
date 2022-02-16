package com.log.eventscommunities.domain.use_case.event


import com.log.eventscommunities.domain.model.FunctionResponse
import com.log.eventscommunities.domain.repository.EventRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageEventUseCase @Inject constructor(
    private val repository: EventRepository,
) {
    suspend operator fun invoke(
        data: HashMap<String, *>,
        isAttending: Boolean,
    ): Flow<Resource<FunctionResponse>> {
        return repository.manageEvent(
            data = data,
            isAttending = isAttending,
        )
    }
}