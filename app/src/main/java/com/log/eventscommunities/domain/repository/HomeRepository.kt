package com.log.eventscommunities.domain.repository

import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun postEvent(data: HashMap<String, *>): Flow<Resource<Any>>

    suspend fun getEvents(): Flow<Resource<List<Event>>>

}