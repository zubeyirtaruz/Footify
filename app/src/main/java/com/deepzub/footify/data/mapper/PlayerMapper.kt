package com.deepzub.footify.data.mapper

import com.deepzub.footify.data.remote.dto.Response
import com.deepzub.footify.domain.model.Footballer

fun Response.toDomain(): Footballer {
    return Footballer(
        id = player.id,
        name = player.name,
        firstname = player.firstname,
        lastname = player.lastname,
        age = player.age,
        nationality = player.nationality,
        photo = player.photo,
        teamName = statistics.firstOrNull()?.team?.name ?: "",
        teamLogo = statistics.firstOrNull()?.team?.logo ?: "",
        position = statistics.firstOrNull()?.games?.position ?: "",
        leagueLogo = statistics.firstOrNull()?.league?.logo ?: "",
        shirtNumber = statistics.firstOrNull()?.games?.number?.toString() ?: ""
    )
}