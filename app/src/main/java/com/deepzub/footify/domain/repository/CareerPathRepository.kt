package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.CareerTeam
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.model.PlayerSeasonStats

interface CareerPathRepository {

    suspend fun getFootballers(
        league: Int,
        season: Int,
    ): List<Footballer>

    suspend fun getTeamsPlayerById(
        id: Int
    ): List<CareerTeam>

    suspend fun getStatisticsByTeamIdAndPlayerName(
         teamId: Int,
         playerName: String
    ): List<PlayerSeasonStats>

}