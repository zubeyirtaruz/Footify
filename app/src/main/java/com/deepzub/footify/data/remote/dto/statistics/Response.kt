package com.deepzub.footify.data.remote.dto.statistics

data class Response(
    val player: Player,
    val statistics: List<Statistic>
)