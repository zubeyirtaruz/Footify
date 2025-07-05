package com.deepzub.footify.domain.use_case.get_one_player

import com.deepzub.footify.domain.model.OnePlayer
import com.deepzub.footify.domain.repository.WhoAreYaRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOnePlayerUseCase @Inject constructor(
    private val repository: WhoAreYaRepository
) {
    operator fun invoke(
        id: Int,
    ): Flow<Resource<List<OnePlayer>>> = flow {
        try {
            emit(Resource.Loading())
            val onePlayer = repository.getPlayerById(id)
            emit(Resource.Success(onePlayer))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}