package com.deepzub.footify.data.repository

import com.deepzub.footify.data.mapper.toDomain
import com.deepzub.footify.data.mapper.toEntity
import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.room.FootballerDao
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository
import javax.inject.Inject

class FootballerRepositoryImpl @Inject constructor(
    private val api: PlayerAPI,
    private val dao: FootballerDao
) : FootballerRepository {

    override suspend fun getFootballers(league: Int, season: Int): List<Footballer> {
        val localFootballers = dao.getAllFootballers().map { it.toDomain() }
        println("Veriler DB den geldi")

        return localFootballers.ifEmpty {
            val remoteFootballers = fetchPlayersRecursively(league, season)
            println("Veriler API dan geldi")
            dao.insertFootballers(remoteFootballers.map { it.toEntity() })
            remoteFootballers

        }
    }

    private suspend fun fetchPlayersRecursively(
        league: Int,
        season: Int,
        page: Int = 1,
        accumulatedPlayers: MutableList<Footballer> = mutableListOf()
    ): List<Footballer> {
        val response = api.getPlayersFromLeague(league, season, page)
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
        dao.deleteAllFootballers()
        val remoteFootballers = fetchPlayersRecursively(league, season)
        dao.insertFootballers(remoteFootballers.map { it.toEntity() })
    }

}
