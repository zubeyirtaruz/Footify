package com.deepzub.footify.data.remote.dto

data class Response(
    val player: Player,
    val statistics: List<Statistic>
)