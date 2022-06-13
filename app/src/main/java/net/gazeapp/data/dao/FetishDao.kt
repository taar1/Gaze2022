package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Fetish

@Dao
interface FetishDao {
    @Query("SELECT * FROM Fetish")
    fun getAll(): List<Fetish>

    @Query("SELECT * FROM Fetish where contact_id = :contactId")
    fun getFetishes(contactId: Int): List<Fetish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fetish: Fetish): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg fetishes: Fetish): List<Long>

    @Delete
    fun delete(fetish: Fetish)

    @Query("DELETE FROM Fetish where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(fetish: Fetish)
}