package com.mahmoudlkashef.shipzone.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("app_prefs")

class DataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IDataStore {

    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    override suspend fun setLanguage(language: String) {
        context.dataStore.edit { it[LANGUAGE_KEY] = language }
    }

    override suspend fun getLanguage(): String {
        return context.dataStore.data
            .map { it[LANGUAGE_KEY] }
            .firstOrNull() ?: Locale.getDefault().language
    }
}