package com.deepzub.footify.domain.use_case.get_player_season_stats

import com.deepzub.footify.domain.model.PlayerSeasonStats
import com.deepzub.footify.domain.repository.CareerPathRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPlayerSeasonStats @Inject constructor(
    private val repository: CareerPathRepository
) {
    operator fun invoke(
        teamId: Int,
        playerName: String
    ): Flow<Resource<List<PlayerSeasonStats>>> = flow {
        try {
            emit(Resource.Loading())
            val playerSeasonStats = repository.getStatisticsByTeamIdAndPlayerName(teamId,playerName)
            emit(Resource.Success(playerSeasonStats))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}