package com.deepzub.footify.domain.use_case.get_clubs

import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.repository.GuessClubRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetClubsUseCase @Inject constructor(
    private val repository: GuessClubRepository
) {
    operator fun invoke(
        league: Int,
        season: Int
    ): Flow<Resource<List<Club>>> = flow {
        try {
            emit(Resource.Loading())
            val clubs = repository.getClubs(league,season)
            emit(Resource.Success(clubs))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}