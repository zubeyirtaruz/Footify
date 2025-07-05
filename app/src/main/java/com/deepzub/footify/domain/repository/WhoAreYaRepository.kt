package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.model.Footballer

interface WhoAreYaRepository {

    suspend fun getFootballers(
        league: Int,
        season: Int,
    ): List<Footballer>

    suspend fun getCountries(): List<Country>

}