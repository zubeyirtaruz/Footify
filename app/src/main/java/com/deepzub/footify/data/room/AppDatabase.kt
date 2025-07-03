package com.deepzub.footify.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FootballerEntity::class, CountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun footballerDao(): FootballerDao
    abstract fun countryDao(): CountryDao
}