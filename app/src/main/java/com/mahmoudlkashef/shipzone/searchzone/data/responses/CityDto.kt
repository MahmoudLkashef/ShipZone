package com.mahmoudlkashef.shipzone.searchzone.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    @SerialName("cityId") val id: String,
    @SerialName("cityName") val name: String,
    @SerialName("districts") val districts: List<DistrictDto>
) 