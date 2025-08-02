package com.deepzub.footify.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClubDao {

    @Query("SELECT * FROM clubs")
    suspend fun getAllClubs(): List<ClubEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubs: List<ClubEntity>)

    @Query("DELETE FROM clubs")
    suspend fun deleteAllClubs()
}