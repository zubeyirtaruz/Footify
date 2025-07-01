package com.deepzub.footify.data.remote.dto

import com.deepzub.footify.domain.model.Footballer

data class PlayerDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)

fun Response.toDomain(): Footballer {
    return Footballer(
        id = player.id,
        name = player.name,
        firstname = player.firstname,
        lastname = player.lastname,
        age = player.age,
        nationality = player.nationality,
        number = player.number,
        position = player.position,
        photo = player.photo
    )
}

