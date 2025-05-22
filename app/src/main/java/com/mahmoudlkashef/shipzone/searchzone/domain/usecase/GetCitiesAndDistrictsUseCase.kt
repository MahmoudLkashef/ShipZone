package com.mahmoudlkashef.shipzone.searchzone.domain.usecase

import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.repository.SearchZoneRepository
import javax.inject.Inject

 class GetCitiesAndDistrictsUseCase @Inject constructor(
    private val repository: SearchZoneRepository
) {
    suspend operator fun invoke(): List<City> = repository.getCitiesAndDistricts()
} 