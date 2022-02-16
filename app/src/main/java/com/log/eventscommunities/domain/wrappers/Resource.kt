package com.log.eventscommunities.domain.wrappers

sealed class Resource<out T: Any>(
    val data: T? = null,
    val exception: Throwable? = null,
    val progress: Double? = null,
) {
    class Initial<T : Any> : Resource<T>()
    class Loading<T : Any>(progress: Double? = null) : Resource<T>(progress = progress)
    class Success<T : Any>(data: T? = null) : Resource<T>(data = data)
    class Error<T : Any>(exception: Throwable? = null) : Resource<T>(exception = exception)
}

fun <T : Any> Resource<T>.isSuccess(): Boolean {
    return this is Resource.Success<T>
}

fun <T : Any> Resource<T>.isFailure(): Boolean {
    return this is Resource.Error
}

fun <T : Any> Resource<T>.isLoading(): Boolean {
    return this is Resource.Loading
}