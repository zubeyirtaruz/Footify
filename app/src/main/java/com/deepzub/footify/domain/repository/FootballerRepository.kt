package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.Footballer

interface FootballerRepository {
    suspend fun getFootballers(
        league: Int,
        season: Int,
    ): List<Footballer>
}