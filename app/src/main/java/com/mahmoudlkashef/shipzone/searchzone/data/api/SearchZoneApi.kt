package com.mahmoudlkashef.shipzone.searchzone.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.mahmoudlkashef.shipzone.searchzone.data.responses.CityDto
import com.mahmoudlkashef.shipzone.searchzone.data.responses.GetDistrictsResponse

interface SearchZoneApi {
    @GET("cities/getAllDistricts")
    suspend fun getAllDistricts(
    ): GetDistrictsResponse
} 