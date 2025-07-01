package com.deepzub.footify.domain.model

data class Footballer(
    val id: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val nationality: String,
    val photo: String,
    val teamName: String,
    val teamLogo: String,
    val leagueLogo: String,
    val position: String,
    val shirtNumber: Any?
)
