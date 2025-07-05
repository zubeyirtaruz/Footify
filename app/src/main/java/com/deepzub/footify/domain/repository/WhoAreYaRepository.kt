package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.model.OnePlayer

interface WhoAreYaRepository {

    suspend fun getFootballers(
        league: Int,
        season: Int,
    ): List<Footballer>

    suspend fun getPlayerById(
        id: Int,
    ): List<OnePlayer>

    suspend fun getCountries(): List<Country>

}