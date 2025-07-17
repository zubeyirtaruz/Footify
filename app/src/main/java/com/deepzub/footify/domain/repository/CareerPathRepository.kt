package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.CareerTeam
import com.deepzub.footify.domain.model.Footballer

interface CareerPathRepository {

    suspend fun getFootballers(
        league: Int,
        season: Int,
    ): List<Footballer>

    suspend fun getTeamsPlayerById(
        id: Int
    ): List<CareerTeam>

}