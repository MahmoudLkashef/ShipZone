package com.mahmoudlkashef.shipzone.core.utils

import android.content.Context
import android.content.res.Configuration
import com.mahmoudlkashef.shipzone.core.data.local.IDataStore
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

class LanguageManager @Inject constructor(private val dataStore: IDataStore) {
    fun setLanguagePreference(language: String) =
        runBlocking { dataStore.setLanguage(language) }

    fun getLanguagePreference(): String = runBlocking { dataStore.getLanguage() }

    fun setLocaleWithLanguagePreference(context: Context): Context? {
        val locale = Locale.Builder().setLanguage(getLanguagePreference()).build()
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}