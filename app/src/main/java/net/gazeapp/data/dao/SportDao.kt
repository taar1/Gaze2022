package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Sport

@Dao
interface SportDao {
    @Query("SELECT * FROM Sport where contact_id = :contactId")
    fun getSports(contactId: String): List<Sport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sport: Sport): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sports: Sport): List<Long>

    @Delete
    fun delete(sport: Sport)

    @Delete
    fun delete(vararg sport: Sport)

    @Update
    fun update(sport: Sport)
}