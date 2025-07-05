package com.deepzub.footify.data.remote.dto.players

data class PlayersDto(
    val `get`: String,
    val paging: Paging,
    val errors: Any,
    val parameters: Parameters,
    val response: List<ResponsePlayers>,
    val results: Int
)