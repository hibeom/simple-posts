package com.pinkcloud.shared.remote

import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                return Result.Success(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (exception: Exception) {
            return error(exception.message ?: exception.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.Error("Network Request Error: $message")
    }
}

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String?, data: T? = null): Result<T>(data, message)
    class Loading<T>: Result<T>()
}