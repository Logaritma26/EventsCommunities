package com.log.eventscommunities.domain.wrappers

sealed class Resource<out T: Any>(
    val data: T? = null,
    val exception: Throwable? = null,
    val progress: Double? = null,
) {
    class Initial : Resource<Nothing>()
    class Loading(progress: Double? = null) : Resource<Nothing>(progress = progress)
    class Success<T: Any>(data: T? = null) : Resource<T>(data = data)
    class Error(exception: Throwable? = null) : Resource<Nothing>(exception = exception)
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