package com.deepzub.footify.data.remote.dto.statistics

data class StatisticsDto(
    val errors: Any,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)