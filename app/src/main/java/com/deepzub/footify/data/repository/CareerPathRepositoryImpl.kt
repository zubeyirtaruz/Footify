package com.deepzub.footify.data.repository

import com.deepzub.footify.data.mapper.toDomain
import com.deepzub.footify.data.mapper.toEntity
import com.deepzub.footify.data.mapper.toPlayerSeasonStatsList
import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.room.FootballerDao
import com.deepzub.footify.domain.model.CareerTeam
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.model.PlayerSeasonStats
import com.deepzub.footify.domain.repository.CareerPathRepository
import com.google.gson.Gson
import javax.inject.Inject

class CareerPathRepositoryImpl @Inject constructor(
    private val playerAPI: PlayerAPI,
    private val footballerDao: FootballerDao,

) : CareerPathRepository {

    override suspend fun getFootballers(league: Int, season: Int): List<Footballer> {
        val localFootballers = footballerDao.getAllFootballers().map { it.toDomain() }

        return localFootballers.ifEmpty {
            val remoteFootballers = fetchPlayersRecursively(league, season)
            footballerDao.insertFootballers(remoteFootballers.map { it.toEntity() })
            remoteFootballers
        }
    }

    override suspend fun getTeamsPlayerById(id: Int): List<CareerTeam> {
        val response = playerAPI.getTeamsPlayerById(id)
        val playersTeams = response.response.map { it.toDomain() }
        return playersTeams
    }

    override suspend fun getStatisticsByTeamIdAndPlayerName(
        teamId: Int,
        playerName: String
    ): List<PlayerSeasonStats> {

        val response = playerAPI.getStatisticsByTeamIdAndPlayerName(teamId, playerName)
//        println("RAW API response: ${Gson().toJson(response)}")
        return response.toPlayerSeasonStatsList()
    }

    private suspend fun fetchPlayersRecursively(
        league: Int,
        season: Int,
        page: Int = 1,
        accumulatedPlayers: MutableList<Footballer> = mutableListOf()
    ): List<Footballer> {
        val response = playerAPI.getPlayersFromLeague(league, season, page)
        val players = response.response.map { it.toDomain() }
        accumulatedPlayers.addAll(players)

        return if (response.paging.current < response.paging.total) {
            fetchPlayersRecursively(league, season, page + 1, accumulatedPlayers)
        } else {
            accumulatedPlayers
        }
    }

    // DB deki verileri güncellemek için
    suspend fun refreshFootballers(league: Int, season: Int) {
        footballerDao.deleteAllFootballers()
        val remoteFootballers = fetchPlayersRecursively(league, season)
        footballerDao.insertFootballers(remoteFootballers.map { it.toEntity() })
    }

}