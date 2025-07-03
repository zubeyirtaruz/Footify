package com.deepzub.footify.data.mapper

import com.deepzub.footify.data.remote.dto.ResponseCountry
import com.deepzub.footify.data.room.CountryEntity
import com.deepzub.footify.domain.model.Country

fun ResponseCountry.toDomain(): Country {
    return Country(
        code = this.code ?: "",
        flag = this.flag ?: "",
        name = this.name ?: ""
    )
}

fun CountryEntity.toDomain(): Country {
    return Country(
        code = code,
        flag = flag,
        name = name
    )
}

fun Country.toEntity(): CountryEntity {
    return CountryEntity(
        code = code,
        flag = flag,
        name = name
    )
}