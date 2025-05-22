package com.mahmoudlkashef.shipzone.searchzone.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DistrictDto(
    @SerialName("zoneId") val zoneId: String,
    @SerialName("districtId") val districtId: String,
    @SerialName("zoneName") val name: String,
    @SerialName("pickupAvailability") val pickupAvailability: Boolean,
    @SerialName("dropOffAvailability") val dropOffAvailability: Boolean
)