package com.deepzub.footify.data.remote.dto

data class PlayerDto(
    val `get`: String,
    val paging: Paging,
    val errors: Any,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)