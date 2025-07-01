package com.deepzub.footify.domain.use_case.get_player

import coil.network.HttpException
import com.deepzub.footify.data.remote.dto.toDomain
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetPlayerUseCase @Inject constructor(private val repository: FootballerRepository) {

//    fun executeGetFootballer(playerId : Int) : Flow<Resource<Footballer>> = flow {
//
//        try {
//            emit(Resource.Loading())
//            val footballer = repository.getFootballer(playerId)
//            if(footballer.results != 0 ){
//                emit(Resource.Success(footballer.response.first().toDomain()))
//
//            }else{
//                emit(Resource.Error(message = "No player found!!!"))
//            }
//
//        } catch (e: IOError) {
//            emit(Resource.Error(message = "No internet connection"))
//        }
//        catch (e: HttpException) {
//            emit(Resource.Error(message = e.localizedMessage ?: "HttpException"))
//        }
//
//
//
//    }
}