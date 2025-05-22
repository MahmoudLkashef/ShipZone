package com.mahmoudlkashef.shipzone.searchzone.domain.model

data class District(
    val zoneId: String,
    val districtId: String,
    val name: String,
    val pickupAvailability: Boolean,
    val dropOffAvailability: Boolean
) 