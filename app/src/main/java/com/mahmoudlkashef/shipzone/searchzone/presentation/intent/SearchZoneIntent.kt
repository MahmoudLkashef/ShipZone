package com.mahmoudlkashef.shipzone.searchzone.presentation.intent

import com.mahmoudlkashef.shipzone.core.presentation.ViewIntent

sealed class SearchZoneIntent :ViewIntent{
    object OnScreenOpened : SearchZoneIntent()
    data class OnSearchQueryChanged(val query: String) : SearchZoneIntent()
    object OnClearSearchQuery : SearchZoneIntent()
    data class OnLanguageChanged(val language: String) : SearchZoneIntent()
}