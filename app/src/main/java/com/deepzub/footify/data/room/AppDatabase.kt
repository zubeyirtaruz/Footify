package com.deepzub.footify.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FootballerEntity::class, CountryEntity::class, ClubEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun footballerDao(): FootballerDao
    abstract fun countryDao(): CountryDao
    abstract fun clubDao(): ClubDao
}