package com.example.harajtask.utils

sealed class Resource<T>(
    val data: T? = null,
    val status: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(status: String, data: T? = null) : Resource<T>(data, status)
    class Loading<T> : Resource<T>()
}
