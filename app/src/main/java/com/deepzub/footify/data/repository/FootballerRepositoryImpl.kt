package com.deepzub.footify.data.repository

import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.remote.dto.toDomain
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository

class FootballerRepositoryImpl(
    private val api: PlayerAPI
) : FootballerRepository {

    override suspend fun getFootballer(playerId: Int): Footballer {
        val response = api.getPlayer(playerId)
        return response.response.first().toDomain()
    }
}