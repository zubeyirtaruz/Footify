package com.deepzub.footify.data.repository

import com.deepzub.footify.data.mapper.toDomain
import com.deepzub.footify.data.mapper.toEntity
import com.deepzub.footify.data.remote.ClubAPI
import com.deepzub.footify.data.remote.CountryAPI
import com.deepzub.footify.data.room.ClubDao
import com.deepzub.footify.data.room.CountryDao
import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.repository.GuessClubRepository
import javax.inject.Inject

class GuessClubRepositoryImpl @Inject constructor(
    private val clubAPI: ClubAPI,
    private val countryAPI: CountryAPI,
    private val clubDao: ClubDao,
    private val countryDao: CountryDao
) : GuessClubRepository {

    override suspend fun getClubs(league: Int, season: Int): List<Club> {
        val countries = getCountries()

        val localClubs = clubDao.getAllClubs().map { it.toDomain() }

        return localClubs.ifEmpty {
            val remoteClubs = clubAPI.getClubsFromLeague(league, season).response
                .map { it.toDomain(countries) }
            clubDao.insertClubs(remoteClubs.map { it.toEntity() })
            remoteClubs
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

    private suspend fun fetchCountries(): List<Country> {
        val response = countryAPI.getCountries()
        val countries = response.response.map { it.toDomain() }
        return countries
    }

    // DB deki verileri güncellemek için
    suspend fun refreshClubs(league: Int, season: Int) {
        val countries = getCountries()
        clubDao.deleteAllClubs()
        val remoteClubs = clubAPI.getClubsFromLeague(league, season).response
            .map { it.toDomain(countries) }
        clubDao.insertClubs(remoteClubs.map { it.toEntity() })
    }

}