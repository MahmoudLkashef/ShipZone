package com.mahmoudlkashef.shipzone.searchzone.presentation.viewState

import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.core.presentation.ViewErrorEffect
import com.mahmoudlkashef.shipzone.core.presentation.ViewNavigation

data class SearchZoneViewState(
    val isLoading: Boolean = false,
    val navigation: ViewNavigation? = null,
    val errorEffect: ViewErrorEffect? = null,
    val cities: List<City> = emptyList(),
    val error: String = "",
    val searchQuery: String = ""
) 