package com.mahmoudlkashef.shipzone.searchzone.domain.repository

import com.mahmoudlkashef.shipzone.searchzone.domain.model.City

interface SearchZoneRepository {
    suspend fun getCitiesAndDistricts(): List<City>
}