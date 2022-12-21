package com.example.dotadex.common

sealed class ResourceState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResourceState<T>(data = data)
    class Failure<T>(message: String) : ResourceState<T>(message = message)
    class Loading<T>() : ResourceState<T>()
}
