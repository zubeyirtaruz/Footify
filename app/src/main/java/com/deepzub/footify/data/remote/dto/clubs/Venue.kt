package com.deepzub.footify.data.remote.dto.clubs

data class Venue(
    val address: String,
    val capacity: Int,
    val city: String,
    val id: Int,
    val image: String,
    val name: String,
    val surface: String
)