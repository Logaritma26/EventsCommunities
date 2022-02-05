package com.log.eventscommunities.data.manager

import com.google.firebase.functions.FirebaseFunctions
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
    ): Resource<Any> = suspendCoroutine { coroutine ->
        functions
            .getHttpsCallable(functionName)
            .call(data)
            .continueWith { task ->
                val result = task.result?.data as Map<*, *>
                if (result["res"] as Boolean) {
                    coroutine.resume(Resource.Success(data = result))
                } else {
                    coroutine.resume(Resource.Error())
                }
            }
    }


    /* suspend fun valueFunction(
         functionName: String,
         data: HashMap<String, *>
     ) {
         val res = suspendCoroutine<Resource<Any>> { coroutine ->
             functions
                 .getHttpsCallable(functionName)
                 .call(data)
                 .continueWith { task ->
                     val result = task.result?.data as Map<*, *>
                     if (result["res"] as Boolean) {
                         coroutine.resume(Resource.Success())
                     } else {
                         coroutine.resume(Resource.Error())
                     }
                 }
         }
     }*/
}