package com.mahmoudlkashef.shipzone.searchzone.data.dataSource

import com.mahmoudlkashef.shipzone.searchzone.data.api.SearchZoneApi
import com.mahmoudlkashef.shipzone.searchzone.data.mapper.toCities
import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import javax.inject.Inject

class SearchZoneRemoteDataSourceImpl @Inject constructor(
    private val api: SearchZoneApi,
): SearchZoneRemoteDataSource{
    override suspend fun getCitiesAndDistricts(): List<City> {
        return api.getAllDistricts().data.toCities()
    }
}