package com.deepzub.footify.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "footballers")
data class FootballerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val nationality: String,
    val photo: String,
    val teamName: String,
    val teamLogo: String,
    val position: String,
    val leagueLogo: String,
    val shirtNumber: String
)