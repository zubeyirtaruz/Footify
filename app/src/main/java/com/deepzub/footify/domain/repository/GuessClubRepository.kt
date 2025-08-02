package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.model.Country

interface GuessClubRepository {

    suspend fun getClubs(
        league: Int,
        season: Int,
    ): List<Club>

    suspend fun getCountries(): List<Country>

}