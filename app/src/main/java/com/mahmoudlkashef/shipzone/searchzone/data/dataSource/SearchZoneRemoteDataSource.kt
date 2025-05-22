package com.mahmoudlkashef.shipzone.searchzone.data.dataSource

import com.mahmoudlkashef.shipzone.searchzone.domain.model.City

interface SearchZoneRemoteDataSource {
    suspend fun getCitiesAndDistricts(): List<City>
}