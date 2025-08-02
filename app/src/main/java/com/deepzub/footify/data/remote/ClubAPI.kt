package com.deepzub.footify.data.remote

import com.deepzub.footify.data.remote.dto.clubs.ClubsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ClubAPI {

    @GET("teams")
    suspend fun getClubsFromLeague(
        @Query("league") leagueId : Int,
        @Query("season") seasonId : Int
    ) : ClubsDto
}