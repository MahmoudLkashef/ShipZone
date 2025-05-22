package com.mahmoudlkashef.shipzone.core.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import com.mahmoudlkashef.shipzone.core.data.local.IDataStore
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

class AppInterceptor(
    private val dataStore: IDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val language = runBlocking { dataStore.getLanguage() }

        val requestBuilder = original.newBuilder().apply {
            header("Accept", "application/json")
            header("Content-Type", "application/json")
            header("Accept-Language", language)
            method(original.method, original.body)
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

fun createGsonConverter(): Converter.Factory {
    val contentType = "application/json".toMediaType()
    val json = Json {
        encodeDefaults = false
        ignoreUnknownKeys = true
    }

    return json.asConverterFactory(contentType)
}