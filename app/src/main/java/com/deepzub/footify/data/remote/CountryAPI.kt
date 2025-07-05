package com.deepzub.footify.data.remote

import com.deepzub.footify.data.remote.dto.country.CountryDto
import retrofit2.http.GET

interface CountryAPI {

    @GET("countries")
    suspend fun getCountries() : CountryDto
}