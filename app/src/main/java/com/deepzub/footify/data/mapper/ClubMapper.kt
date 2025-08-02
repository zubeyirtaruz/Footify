package com.deepzub.footify.data.mapper

import com.deepzub.footify.data.remote.dto.clubs.ResponseClub
import com.deepzub.footify.data.room.ClubEntity
import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.model.Country

fun ResponseClub.toDomain(countries: List<Country>): Club {
    val flag = countries.find { it.name.equals(team.country, ignoreCase = true) }?.flag ?: ""
    return Club(
        id = team.id,
        name = team.name,
        country = team.country,
        founded = team.founded,
        logoUrl = team.logo,
        stadiumName = venue.name,
        stadiumCapacity = venue.capacity,
        flagUrl = flag
    )
}

fun ClubEntity.toDomain(): Club {
    return Club(
        id = id,
        name = name,
        country = country,
        founded = founded,
        logoUrl = logoUrl,
        stadiumName = stadiumName,
        stadiumCapacity = stadiumCapacity,
        flagUrl = flagUrl
    )
}

fun Club.toEntity(): ClubEntity {
    return ClubEntity(
        id = id,
        name = name,
        country = country,
        founded = founded,
        logoUrl = logoUrl,
        stadiumName = stadiumName,
        stadiumCapacity = stadiumCapacity,
        flagUrl = flagUrl
    )
}