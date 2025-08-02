package com.deepzub.footify.domain.model

data class Club(
    val id: Int,
    val name: String,
    val country: String,
    val founded: Int,
    val logoUrl: String,
    val stadiumName: String,
    val stadiumCapacity: Int,
    val flagUrl: String = ""
)