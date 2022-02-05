package com.log.eventscommunities.data.repository

import com.log.eventscommunities.data.manager.FirebaseFunctionManager
import com.log.eventscommunities.di.IoDispatcher
import com.log.eventscommunities.domain.repository.HomeRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val functionManager: FirebaseFunctionManager,
) : HomeRepository {


    override suspend fun postEvent(data: HashMap<String, String>): Flow<Resource<Any>> = flow {

        emit(Resource.Loading())

        val res2 = functionManager.fireFunction(
            functionName = "postEvent",
            data = data,
        )

        /*val res = suspendCoroutine<Resource<Any>> { coroutine ->
            functions
                .getHttpsCallable("postEvent")
                .call(data)
                .continueWith { task ->
                    val result = task.result?.data as Map<*, *>
                    if (result["res"] as Boolean) {
                        coroutine.resume(Resource.Success())
                    } else {
                        coroutine.resume(Resource.Error())
                    }
                }
        }*/
        emit(res2)
    }.flowOn(ioDispatcher)

}