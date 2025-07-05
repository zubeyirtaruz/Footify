package com.deepzub.footify.data.remote

import com.deepzub.footify.data.remote.dto.oneplayer.OnePlayerDto
import com.deepzub.footify.data.remote.dto.players.PlayersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerAPI {

    @GET("players")
    suspend fun getPlayersFromLeague(
        @Query("league") leagueId : Int,
        @Query("season") seasonId : Int,
        @Query("page") page: Int
    ) : PlayersDto

    @GET("players/profiles")
    suspend fun getPlayerById(
        @Query("player") id : Int,
    ) : OnePlayerDto

}