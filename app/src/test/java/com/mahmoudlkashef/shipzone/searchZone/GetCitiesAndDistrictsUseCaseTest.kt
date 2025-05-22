package com.mahmoudlkashef.shipzone.searchZone

import com.mahmoudlkashef.shipzone.searchzone.domain.repository.SearchZoneRepository
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.GetCitiesAndDistrictsUseCase
import com.mahmoudlkashef.shipzone.testdispatcher.TestDispatcherExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
class GetCitiesAndDistrictsUseCaseTest {

    private lateinit var getCitiesAndDistrictsUseCase: GetCitiesAndDistrictsUseCase

    private val repository = mockk<SearchZoneRepository>()


    @BeforeEach
    fun setUp() {
        getCitiesAndDistrictsUseCase = GetCitiesAndDistrictsUseCase(repository)
        coEvery { repository.getCitiesAndDistricts() } returns emptyList()
    }

    @Test
    fun `when getCitiesAndDistrictsUseCase is invoked then expected getCitiesAndDistricts should be invoked once`() =
        runTest {
            getCitiesAndDistrictsUseCase()

            coVerify(atLeast = 1) { repository.getCitiesAndDistricts() }
        }
}