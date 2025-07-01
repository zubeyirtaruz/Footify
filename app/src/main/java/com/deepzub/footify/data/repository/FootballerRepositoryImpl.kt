package com.deepzub.footify.data.repository

import com.deepzub.footify.data.mapper.toDomain
import com.deepzub.footify.data.remote.PlayerAPI

import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository

class FootballerRepositoryImpl(
    private val api: PlayerAPI
) : FootballerRepository {

    override suspend fun getFootballers(league: Int, season: Int): List<Footballer> {
        return api.getPlayersFromLeague(league, season).response.map { it.toDomain() }
    }
}