package com.deepzub.footify.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FootballerDao {

    @Query("SELECT * FROM footballers")
    suspend fun getAllFootballers(): List<FootballerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFootballers(footballers: List<FootballerEntity>)

    @Query("DELETE FROM footballers")
    suspend fun deleteAllFootballers()
}