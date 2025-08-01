package com.deepzub.footify.data.remote.dto.statistics

data class Games(
    val appearences: Int,
    val captain: Boolean,
    val lineups: Int,
    val minutes: Int,
    val number: Any,
    val position: String,
    val rating: String
)