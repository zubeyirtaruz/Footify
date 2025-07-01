package com.deepzub.footify.data.remote

import com.deepzub.footify.data.remote.dto.PlayerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerAPI {

    @GET("players/profiles")
    suspend fun getPlayer(
        @Query("player") playerId : Int,
    ) : PlayerDto
}