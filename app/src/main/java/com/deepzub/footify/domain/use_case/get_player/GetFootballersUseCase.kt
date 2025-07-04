package com.deepzub.footify.domain.use_case.get_player

import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFootballersUseCase @Inject constructor(
    private val repository: FootballerRepository
) {
    operator fun invoke(
        league: Int,
        season: Int
    ): Flow<Resource<List<Footballer>>> = flow {
        try {
            emit(Resource.Loading())
            val footballers = repository.getFootballers(league, season)
            emit(Resource.Success(footballers))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}