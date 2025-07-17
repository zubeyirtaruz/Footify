package com.deepzub.footify.data.remote.dto.teams

data class TeamsDto(
    val errors: Any,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<ResponseTeam>,
    val results: Int
)