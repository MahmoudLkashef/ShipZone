package com.mahmoudlkashef.shipzone.searchzone.domain.model

data class City(
    val id: String,
    val name: String,
    val districts: List<District>
) 