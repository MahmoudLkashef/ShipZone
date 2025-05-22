package com.mahmoudlkashef.shipzone.searchzone.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mahmoudlkashef.shipzone.core.presentation.ErrorEffects
import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.GetCitiesAndDistrictsUseCase
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.SearchCitiesAndDistrictsUseCase
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.UpdateLanguagePreferenceUseCase
import com.mahmoudlkashef.shipzone.searchzone.presentation.intent.SearchZoneIntent
import com.mahmoudlkashef.shipzone.searchzone.presentation.navigation.SearchZoneNavigation
import com.mahmoudlkashef.shipzone.searchzone.presentation.viewState.SearchZoneViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchZoneViewModel @Inject constructor(
    private val getCitiesAndDistrictsUseCase: GetCitiesAndDistrictsUseCase,
    private val searchCitiesAndDistrictsUseCase: SearchCitiesAndDistrictsUseCase,
    private val updateLanguagePreferenceUseCase: UpdateLanguagePreferenceUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchZoneViewState())
    val state: StateFlow<SearchZoneViewState> = _state.asStateFlow()

    private val _effect = Channel<ErrorEffects?>()
    val errorEffect = _effect.receiveAsFlow()

    private val _intents = MutableSharedFlow<SearchZoneIntent>(extraBufferCapacity = 1)

    private var lastIntent: SearchZoneIntent? = null

    private var loadedCities: List<City> = emptyList()

    init {
        subscribeToEvents()
    }

    private fun reduce(intent: SearchZoneIntent) {
        lastIntent = intent
        when (intent) {
            is SearchZoneIntent.OnScreenOpened -> handleOnScreenOpened()
            is SearchZoneIntent.OnSearchQueryChanged -> handleOnSearchQueryChanged(intent.query)
            is SearchZoneIntent.OnClearSearchQuery -> handleOnClearSearchQuery()
            is SearchZoneIntent.OnLanguageChanged -> handleOnLanguageChanged(intent.language)
        }
    }

    private fun handleOnLanguageChanged(language: String) {
        updateLanguagePreferenceUseCase(language)
        _state.value = _state.value.copy(navigation = SearchZoneNavigation.ToHomeScreen)
    }

    private fun handleOnScreenOpened() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val cities = getCitiesAndDistrictsUseCase()
                loadedCities = cities
                _state.value = _state.value.copy(isLoading = false, cities = cities)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message.orEmpty())
                _effect.send(ErrorEffects.ShowBlockingError)
            }
        }
    }

    private fun handleOnSearchQueryChanged(query: String) {
        viewModelScope.launch {
            val filteredCities = searchCitiesAndDistrictsUseCase(loadedCities, query)
            _state.value = _state.value.copy(searchQuery = query, cities = filteredCities)
        }
    }

    private fun handleOnClearSearchQuery() {
        _state.value = _state.value.copy(searchQuery = "", cities = loadedCities)
    }

    fun sendIntent(event: SearchZoneIntent) {
        _intents.tryEmit(event)
    }

    fun retryLastIntent() {
        lastIntent?.let { reduce(it) }
    }

    fun setErrorEffect(errorEffect: ErrorEffects) {
        viewModelScope.launch {
            _effect.send(errorEffect)
        }
    }

    fun resetError() {
        viewModelScope.launch {
            _effect.send(null)
        }
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _intents.collect{
                reduce(it)
            }
        }
    }
}
