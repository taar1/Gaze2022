package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Club

@Dao
interface ClubDao {
    @Query("SELECT * FROM Club")
    fun getAll(): List<Club>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(club: Club): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg clubs: Club): List<Long>

    @Delete
    fun delete(club: Club)

    @Update
    fun update(club: Club)
}