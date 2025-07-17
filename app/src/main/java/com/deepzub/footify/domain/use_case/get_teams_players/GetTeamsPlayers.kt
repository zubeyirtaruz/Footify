package com.deepzub.footify.domain.use_case.get_teams_players

import com.deepzub.footify.domain.model.CareerTeam
import com.deepzub.footify.domain.repository.CareerPathRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTeamsPlayers @Inject constructor(
    private val repository: CareerPathRepository
) {
    operator fun invoke(
        id: Int
    ): Flow<Resource<List<CareerTeam>>> = flow {
        try {
            emit(Resource.Loading())
            val teams = repository.getTeamsPlayerById(id)
            emit(Resource.Success(teams))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}