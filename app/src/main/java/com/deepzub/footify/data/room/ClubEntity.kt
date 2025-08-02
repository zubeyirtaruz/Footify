package com.deepzub.footify.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clubs")
data class ClubEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String,
    val founded: Int,
    val logoUrl: String,
    val stadiumName: String,
    val stadiumCapacity: Int,
    val flagUrl: String
)