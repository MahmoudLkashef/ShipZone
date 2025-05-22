package com.mahmoudlkashef.shipzone.searchzone.domain.usecase

import com.mahmoudlkashef.shipzone.core.utils.LanguageManager
import javax.inject.Inject

class UpdateLanguagePreferenceUseCase @Inject constructor(private val languageManager: LanguageManager) {
    operator fun invoke(language: String) {
        languageManager.setLanguagePreference(language)
    }
}