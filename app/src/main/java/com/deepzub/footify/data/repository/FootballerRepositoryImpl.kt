package com.deepzub.footify.data.repository

import com.deepzub.footify.data.mapper.toDomain
import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository
import javax.inject.Inject

class FootballerRepositoryImpl @Inject constructor(
    private val api: PlayerAPI
) : FootballerRepository {

    override suspend fun getFootballers(league: Int, season: Int): List<Footballer> {
        return fetchPlayersRecursively(league, season)
    }

    private suspend fun fetchPlayersRecursively(
        league: Int,
        season: Int,
        page: Int = 1,
        accumulatedPlayers: MutableList<Footballer> = mutableListOf()
    ): List<Footballer> {
        val response = api.getPlayersFromLeague(league, season, page)
        println(response.errors.toString())


        val players = response.response.map { it.toDomain() }
        accumulatedPlayers.addAll(players)

        return if (response.paging.current < response.paging.total) {
            fetchPlayersRecursively(league, season, page + 1, accumulatedPlayers)
        } else {
            accumulatedPlayers
        }

    }

}