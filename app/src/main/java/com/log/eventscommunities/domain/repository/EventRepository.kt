package com.log.eventscommunities.domain.repository

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.FunctionResponse
import com.log.eventscommunities.domain.model.UserDocument
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun postEvent(data: HashMap<String, *>): Flow<Resource<FunctionResponse>>

    suspend fun manageEvent(
        data: HashMap<String, *>,
        isAttending: Boolean,
    ): Flow<Resource<FunctionResponse>>

    suspend fun watchEvent(eventId: String): Flow<Resource<Event>>

    suspend fun getEvents(): Flow<Resource<List<Event>>>

    suspend fun getAttendingEvents(userId: String): Flow<Resource<UserDocument>>
}