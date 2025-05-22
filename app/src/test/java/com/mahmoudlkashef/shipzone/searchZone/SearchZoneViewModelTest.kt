package com.mahmoudlkashef.shipzone.searchZone

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoudlkashef.shipzone.core.presentation.ErrorEffects
import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.model.District
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.GetCitiesAndDistrictsUseCase
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.SearchCitiesAndDistrictsUseCase
import com.mahmoudlkashef.shipzone.searchzone.presentation.intent.SearchZoneIntent
import com.mahmoudlkashef.shipzone.searchzone.presentation.viewModel.SearchZoneViewModel
import com.mahmoudlkashef.shipzone.testdispatcher.TestDispatcherExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
class SearchZoneViewModelTest {

    private val getCitiesAndDistrictsUseCase = mockk<GetCitiesAndDistrictsUseCase>()
    private val searchCitiesAndDistrictsUseCase = mockk<SearchCitiesAndDistrictsUseCase>()
    private lateinit var viewModel: SearchZoneViewModel

    private val sampleCities = listOf(
        City(
            id = "1", name = "Cairo", districts = listOf(
                District(
                    districtId = "11",
                    name = "Maadi",
                    zoneId = "1",
                    pickupAvailability = true,
                    dropOffAvailability = true
                ),
                District(
                    districtId = "12",
                    name = "Nasr City",
                    zoneId = "2",
                    pickupAvailability = true,
                    dropOffAvailability = true
                ),
            )
        ),
        City(
            id = "2", name = "Alexandria", districts = listOf(
                District(
                    districtId = "15",
                    name = "Montazah",
                    zoneId = "4",
                    pickupAvailability = true,
                    dropOffAvailability = true
                ),
                District(
                    districtId = "16",
                    name = "Somuha",
                    zoneId = "5",
                    pickupAvailability = true,
                    dropOffAvailability = true
                ),
            )
        )
    )

    @BeforeEach
    fun setup() {
        coEvery { getCitiesAndDistrictsUseCase() } returns sampleCities
        coEvery { searchCitiesAndDistrictsUseCase(any(), any()) } returns sampleCities

        viewModel = SearchZoneViewModel(
            getCitiesAndDistrictsUseCase, searchCitiesAndDistrictsUseCase
        )
    }

    @Test
    fun `when user typed search query then expected the typed query should be displayed`() {
        val searchQuery = "Cairo"

        viewModel.sendIntent(SearchZoneIntent.OnSearchQueryChanged(searchQuery))

        assertThat(viewModel.state.value.searchQuery).isEqualTo(searchQuery)
    }

    @Test
    fun `when user typed search query then expected searchCitiesAndDistrictsUseCase should be invoked`() {
        val searchQuery = "Cairo"

        viewModel.sendIntent(SearchZoneIntent.OnSearchQueryChanged(searchQuery))

        coVerify(atLeast = 1) { searchCitiesAndDistrictsUseCase(any(), any()) }
    }

    @Test
    fun `when user opened the screen then expected getCitiesAndDistrictsUseCase should be invoked`() {
        viewModel.sendIntent(SearchZoneIntent.OnScreenOpened)

        coVerify(atLeast = 1) { getCitiesAndDistrictsUseCase() }
    }

    @Test
    fun `given error occurred while fetching cities and districts then expected blocking screen error should be displayed`() =
        runTest {
            val errorMessage = "Network error"
            coEvery { getCitiesAndDistrictsUseCase() } throws Exception(errorMessage)

            viewModel.sendIntent(SearchZoneIntent.OnScreenOpened)
            viewModel.errorEffect.test {
                val effect = awaitItem()
                assertThat(effect).isEqualTo(ErrorEffects.ShowBlockingError)
            }
        }

    @Test
    fun `when user clear search query then expected the search query should be empty`() {
        viewModel.sendIntent(SearchZoneIntent.OnClearSearchQuery)

        assertThat(viewModel.state.value.searchQuery).isEmpty()
    }
}