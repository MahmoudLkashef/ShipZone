package com.mahmoudlkashef.shipzone.searchzone.data.repository

import com.mahmoudlkashef.shipzone.searchzone.data.dataSource.SearchZoneRemoteDataSource
import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.repository.SearchZoneRepository
import javax.inject.Inject

class SearchZoneRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchZoneRemoteDataSource
) : SearchZoneRepository {
    override suspend fun getCitiesAndDistricts(): List<City> {
        return remoteDataSource.getCitiesAndDistricts()
    }
} 