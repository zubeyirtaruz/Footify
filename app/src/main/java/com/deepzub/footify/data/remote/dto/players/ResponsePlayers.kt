package com.deepzub.footify.data.remote.dto.players

data class ResponsePlayers(
    val player: Player,
    val statistics: List<Statistic>
)