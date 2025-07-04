package com.deepzub.footify.domain.use_case.get_country

import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.repository.FootballerRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: FootballerRepository
) {
    operator fun invoke(
    ): Flow<Resource<List<Country>>> = flow {
        try {
            emit(Resource.Loading())
            val countries = repository.getCountries()
            emit(Resource.Success(countries))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}