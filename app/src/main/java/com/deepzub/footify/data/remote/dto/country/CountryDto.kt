package com.deepzub.footify.data.remote.dto.country

data class CountryDto(
    val errors: List<Any?>,
    val `get`: String,
    val paging: Paging,
    val parameters: List<Any?>,
    val response: List<ResponseCountry>,
    val results: Int
)