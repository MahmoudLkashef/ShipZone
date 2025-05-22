package com.mahmoudlkashef.shipzone.searchzone.domain.usecase

import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import javax.inject.Inject

class SearchCitiesAndDistrictsUseCase @Inject constructor() {
    suspend operator fun invoke(cities: List<City>, query: String): List<City> {
        if (query.isBlank()) return cities
        val lowerQuery = query.trim().lowercase()
        return cities.mapNotNull { city ->
            val cityMatches = city.name.contains(lowerQuery, ignoreCase = true)
            val filteredDistricts = city.districts.filter { district ->
                district.name.contains(lowerQuery, ignoreCase = true)
            }
            if (cityMatches || filteredDistricts.isNotEmpty()) {
                city.copy(districts = if (cityMatches) city.districts else filteredDistricts)
            } else null
        }
    }
} 