package com.mahmoudlkashef.shipzone.core.data.local

interface IDataStore {
    suspend fun setLanguage(language: String)
    suspend fun getLanguage(): String
}