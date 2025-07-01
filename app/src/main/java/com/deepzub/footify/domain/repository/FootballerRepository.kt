package com.deepzub.footify.domain.repository

import com.deepzub.footify.domain.model.Footballer

interface FootballerRepository {
    suspend fun getFootballer(playerId: Int): Footballer
}