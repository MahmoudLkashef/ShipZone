package com.mahmoudlkashef.shipzone.searchzone.data.repository

import com.mahmoudlkashef.shipzone.core.data.local.IDataStore
import com.mahmoudlkashef.shipzone.searchzone.data.api.SearchZoneApi
import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.model.District
import com.mahmoudlkashef.shipzone.searchzone.domain.repository.SearchZoneRepository
import javax.inject.Inject

class SearchZoneRepositoryImpl @Inject constructor(
    private val api: SearchZoneApi,
    private val dataStore:IDataStore
) : SearchZoneRepository {
    override suspend fun getCitiesAndDistricts(): List<City> {
        return api.getAllDistricts().data.map { cityDto ->
            City(
                id = cityDto.id,
                name = cityDto.name,
                districts = cityDto.districts.map { district->
                    District(
                        zoneId = district.zoneId,
                        districtId = district.districtId,
                        name = district.name,
                        dropOffAvailability = district.dropOffAvailability,
                        pickupAvailability = district.pickupAvailability
                    )
                }
            )
        }
    }
} 