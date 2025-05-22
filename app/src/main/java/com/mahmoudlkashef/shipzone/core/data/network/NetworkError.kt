package com.mahmoudlkashef.shipzone.core.data.network

sealed class NetworkError : Throwable() {
    object NoInternet : NetworkError()
    data class ApiError(val code: Int, val errorMessage: String) : NetworkError()
    object Unknown : NetworkError()
} 