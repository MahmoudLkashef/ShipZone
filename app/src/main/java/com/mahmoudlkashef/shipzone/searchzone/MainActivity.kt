package com.mahmoudlkashef.shipzone.searchzone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mahmoudlkashef.shipzone.core.data.local.DataStoreImpl
import com.mahmoudlkashef.shipzone.core.utils.LanguageManager
import com.mahmoudlkashef.shipzone.searchzone.presentation.view.SearchZoneScreen
import com.mahmoudlkashef.shipzone.ui.theme.ShipZoneTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShipZoneTheme {
                SearchZoneScreen{
                    start(this)
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val dataStore = DataStoreImpl(newBase)
        val languageManager = LanguageManager(dataStore)
        val context = languageManager.setLocaleWithLanguagePreference(newBase)
        super.attachBaseContext(context)
    }
}
