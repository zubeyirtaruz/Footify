package com.deepzub.footify.data.remote.dto.oneplayer

data class OnePlayerDto(
    val errors: Any?,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<ResponseOnePlayer>,
    val results: Int
)