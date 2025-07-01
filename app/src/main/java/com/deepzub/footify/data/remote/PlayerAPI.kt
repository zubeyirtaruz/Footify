package com.deepzub.footify.data.remote

import com.deepzub.footify.data.remote.dto.PlayerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerAPI {

//    players?league=39&season=2023 Premier ligten

    @GET("players")
    suspend fun getPlayersFromLeague(
        @Query("league") leagueId : Int,
        @Query("season") seasonId : Int,
    ) : PlayerDto
}