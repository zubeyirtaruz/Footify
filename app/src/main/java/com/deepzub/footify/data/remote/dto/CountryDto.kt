package com.deepzub.footify.data.remote.dto

data class CountryDto(
    val errors: List<Any?>,
    val `get`: String,
    val paging: PagingCountry,
    val parameters: List<Any?>,
    val response: List<ResponseCountry>,
    val results: Int
)