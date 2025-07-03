package com.deepzub.footify.domain.model

data class Footballer(
    val id: Int,
    val name: String,
    val age: Int,
    val nationality: String,
    val photo: String,
    val teamName: String,
    val teamLogo: String,
    val position: String,
    val leagueLogo: String,
    val shirtNumber: String
)
