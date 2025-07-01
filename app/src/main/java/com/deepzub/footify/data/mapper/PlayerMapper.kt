package com.deepzub.footify.data.mapper

import com.deepzub.footify.data.remote.dto.Response
import com.deepzub.footify.domain.model.Footballer

fun Response.toDomain(): Footballer = Footballer(
    id = player.id,
    name = player.name,
    firstname = player.firstname,
    lastname = player.lastname,
    age = player.age,
    nationality = player.nationality,
    photo = player.photo,
    teamName = statistics.firstOrNull()?.team?.name ?: "Unknown",
    teamLogo = statistics.firstOrNull()?.team?.logo ?: "",
    leagueLogo = statistics.firstOrNull()?.league?.logo ?: "",
    position = statistics.firstOrNull()?.games?.position ?: "Unknown",
    shirtNumber = statistics.firstOrNull()?.games?.number
)