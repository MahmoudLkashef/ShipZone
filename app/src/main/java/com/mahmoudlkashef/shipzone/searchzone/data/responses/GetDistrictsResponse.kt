package com.mahmoudlkashef.shipzone.searchzone.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDistrictsResponse(
    @SerialName("data")
    val data: List<CityDto>
)
