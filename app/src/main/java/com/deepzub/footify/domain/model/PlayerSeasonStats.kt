package com.deepzub.footify.domain.model

data class PlayerSeasonStats(
    val season: Int,
    val teamName: String,
    val appearances: Int,
    val goals: Int
)