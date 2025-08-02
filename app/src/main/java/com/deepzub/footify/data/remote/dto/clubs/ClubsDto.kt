package com.deepzub.footify.data.remote.dto.clubs

data class ClubsDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<ResponseClub>,
    val results: Int
)