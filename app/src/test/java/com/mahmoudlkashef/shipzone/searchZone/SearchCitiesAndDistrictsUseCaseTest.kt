package com.mahmoudlkashef.shipzone.searchZone

import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.model.District
import com.mahmoudlkashef.shipzone.searchzone.domain.usecase.SearchCitiesAndDistrictsUseCase
import com.mahmoudlkashef.shipzone.testdispatcher.TestDispatcherExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
class SearchCitiesAndDistrictsUseCaseTest {
    private lateinit var searchUseCase: SearchCitiesAndDistrictsUseCase

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
    fun setUp() {
        searchUseCase = SearchCitiesAndDistrictsUseCase()
    }

    @Test
    fun `given empty query when search is performed then all cities should be returned`() = runTest {
        val query = ""
        val result = searchUseCase(sampleCities, query)
        assertEquals(sampleCities, result)
    }

    @Test
    fun `given blank query when search is performed then all cities should be returned`() = runTest {
        val query = " "
        val result = searchUseCase(sampleCities, query)
        assertEquals(sampleCities, result)
    }

    @Test
    fun `given city name match when search is performed then matching city with all districts should be returned`() = runTest {
        val query = "Cai"
        val result = searchUseCase(sampleCities, query)
        assertEquals(
            listOf(
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
                )
            ),
            result
        )
    }

    @Test
    fun `given district name match when search is performed then matching city with matching districts should be returned`() = runTest {
        val query = "Maa"
        val result = searchUseCase(sampleCities, query)
        assertEquals(
            listOf(
                City(
                    id = "1", name = "Cairo", districts = listOf(
                        District(
                            districtId = "11",
                            name = "Maadi",
                            zoneId = "1",
                            pickupAvailability = true,
                            dropOffAvailability = true
                        )
                    )
                )
            ),
            result
        )
    }

    @Test
    fun `given case insensitive query when search is performed then matching city and districts should be returned`() = runTest {
        val query = "caiRO"
        val result = searchUseCase(sampleCities, query)
        assertEquals(
            listOf(
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
                )
            ),
            result
        )
    }

    @Test
    fun `given unmatched query when search is performed then empty list should be returned`() = runTest {
        val query = "Giza"
        val result = searchUseCase(sampleCities, query)
        assertEquals(emptyList<City>(), result)
    }

    @Test
    fun `given empty cities list when search is performed then empty list should be returned`() = runTest {
        val cities = emptyList<City>()
        val query = "Cairo"
        val result = searchUseCase(cities, query)
        assertEquals(emptyList<City>(), result)
    }

    @Test
    fun `given query matching multiple cities and districts when search is performed then all matching results should be returned`() = runTest {
        val cities = listOf(
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
                        name = "Somuha",
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
                        name = "Somuha",
                        zoneId = "4",
                        pickupAvailability = true,
                        dropOffAvailability = true
                    ),
                    District(
                        districtId = "16",
                        name = "Montazah",
                        zoneId = "5",
                        pickupAvailability = true,
                        dropOffAvailability = true
                    ),
                )
            )
        )
        val query = "Somuha"
        val result = searchUseCase(cities, query)
        assertEquals(
            listOf(
                City(
                    id = "1", name = "Cairo", districts = listOf(
                        District(
                            districtId = "12",
                            name = "Somuha",
                            zoneId = "2",
                            pickupAvailability = true,
                            dropOffAvailability = true
                        )
                    )
                ),
                City(
                    id = "2", name = "Alexandria", districts = listOf(
                        District(
                            districtId = "15",
                            name = "Somuha",
                            zoneId = "4",
                            pickupAvailability = true,
                            dropOffAvailability = true
                        )
                    )
                )
            ),
            result
        )
    }


}