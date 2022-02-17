package com.log.eventscommunities.data.manager

import com.google.firebase.functions.FirebaseFunctions
import com.log.eventscommunities.domain.model.FunctionResponse
import com.log.eventscommunities.domain.wrappers.Resource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseFunctionManager @Inject constructor(
    private val functions: FirebaseFunctions
) {

    suspend fun fireFunction(
        functionName: String,
        data: HashMap<String, *>
    ): Resource<FunctionResponse> = suspendCoroutine { coroutine ->
        functions
            .getHttpsCallable(functionName)
            .call(data)
            .continueWith { task ->
                val response = task.result?.data as Map<*, *>
                val res = FunctionResponse(
                    result = response["res"] as Boolean,
                    error = response["error"] as String,
                )
                if (res.result) {
                    coroutine.resume(Resource.Success(data = res))
                } else {
                    coroutine.resume(Resource.Error())
                }
            }
    }
}