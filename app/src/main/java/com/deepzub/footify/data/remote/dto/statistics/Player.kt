package com.deepzub.footify.data.remote.dto.statistics

data class Player(
    val age: Int,
    val birth: Birth,
    val firstname: String,
    val height: String,
    val id: Int,
    val injured: Boolean,
    val lastname: String,
    val name: String,
    val nationality: String,
    val photo: String,
    val weight: String
)