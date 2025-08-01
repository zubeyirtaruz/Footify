package com.deepzub.footify.data.mapper

import com.deepzub.footify.data.remote.dto.oneplayer.ResponseOnePlayer
import com.deepzub.footify.data.remote.dto.players.ResponsePlayers
import com.deepzub.footify.data.remote.dto.statistics.StatisticsDto
import com.deepzub.footify.data.remote.dto.teams.ResponseTeam
import com.deepzub.footify.data.room.FootballerEntity
import com.deepzub.footify.domain.model.CareerTeam
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.model.OnePlayer
import com.deepzub.footify.domain.model.PlayerSeasonStats

fun ResponsePlayers.toDomain(): Footballer {
    return Footballer(
        id = player.id,
        name = player.name,
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

fun ResponseOnePlayer.toDomain(): OnePlayer {
    return OnePlayer(
        id = player.id,
        shirtNumber = player.number
    )
}

fun FootballerEntity.toDomain(): Footballer {
    return Footballer(
        id = id,
        name = name,
        age = age,
        nationality = nationality,
        photo = photo,
        teamName = teamName,
        teamLogo = teamLogo,
        position = position,
        leagueLogo = leagueLogo,
        shirtNumber = shirtNumber
    )
}

fun Footballer.toEntity(): FootballerEntity {
    return FootballerEntity(
        id = id,
        name = name,
        age = age,
        nationality = nationality,
        photo = photo,
        teamName = teamName,
        teamLogo = teamLogo,
        position = position,
        leagueLogo = leagueLogo,
        shirtNumber = shirtNumber
    )
}

fun ResponseTeam.toDomain(): CareerTeam {
    return CareerTeam(
        id = team.id,
        name = team.name,
        seasons = seasons
    )
}

fun StatisticsDto.toPlayerSeasonStatsList(): List<PlayerSeasonStats> {
    return response.flatMap { responseItem ->
        responseItem.statistics.mapNotNull { stat ->
            val season = stat.league.season
            val teamName = stat.team.name
            val appearances = stat.games.appearences ?: 0
            val goals = stat.goals.total ?: 0

            PlayerSeasonStats(
                season = season,
                teamName = teamName,
                appearances = appearances,
                goals = goals
            )
        }
    }
}