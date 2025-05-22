package com.mahmoudlkashef.shipzone.searchzone.data.mapper

import com.mahmoudlkashef.shipzone.searchzone.data.responses.CityDto
import com.mahmoudlkashef.shipzone.searchzone.data.responses.DistrictDto
import com.mahmoudlkashef.shipzone.searchzone.domain.model.City
import com.mahmoudlkashef.shipzone.searchzone.domain.model.District

 fun List<CityDto>.toCities(): List<City> {
    return map { it.toCity() }
}

 fun CityDto.toCity(): City {
    return City(
        id = id,
        name = name,
        districts = districts.toDistricts()
    )
}

 fun List<DistrictDto>.toDistricts(): List<District> {
    return map { it.toDistrict() }
}

 fun DistrictDto.toDistrict(): District {
    return District(
        zoneId = zoneId,
        districtId = districtId,
        name = name,
        dropOffAvailability = dropOffAvailability,
        pickupAvailability = pickupAvailability
    )
}