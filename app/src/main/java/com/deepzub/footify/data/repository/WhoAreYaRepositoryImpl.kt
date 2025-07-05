package com.deepzub.footify.data.repository

import com.deepzub.footify.data.mapper.toDomain
import com.deepzub.footify.data.mapper.toEntity
import com.deepzub.footify.data.remote.CountryAPI
import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.room.CountryDao
import com.deepzub.footify.data.room.FootballerDao
import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.WhoAreYaRepository
import javax.inject.Inject

class WhoAreYaRepositoryImpl @Inject constructor(
    private val playerAPI: PlayerAPI,
    private val countryAPI: CountryAPI,
    private val footballerDao: FootballerDao,
    private val countryDao: CountryDao

) : WhoAreYaRepository {

    override suspend fun getFootballers(league: Int, season: Int): List<Footballer> {
        val localFootballers = footballerDao.getAllFootballers().map { it.toDomain() }

        return localFootballers.ifEmpty {
            val remoteFootballers = fetchPlayersRecursively(league, season)
            footballerDao.insertFootballers(remoteFootballers.map { it.toEntity() })
            remoteFootballers
        }
    }

    override suspend fun getCountries(): List<Country> {

        val localCountries = countryDao.getAllCountries().map { it.toDomain() }

        return localCountries.ifEmpty {
            val remoteCountries = fetchCountries()
            countryDao.insertCountries(remoteCountries.map { it.toEntity() })
            remoteCountries
        }
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

    private suspend fun fetchCountries(): List<Country> {
        val response = countryAPI.getCountries()
        val countries = response.response.map { it.toDomain() }
        return countries
    }

    // DB deki verileri güncellemek için
    suspend fun refreshFootballers(league: Int, season: Int) {
        footballerDao.deleteAllFootballers()
        val remoteFootballers = fetchPlayersRecursively(league, season)
        footballerDao.insertFootballers(remoteFootballers.map { it.toEntity() })
    }

}
