package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Encounter

@Dao
interface EncounterDao {
    @Query("SELECT * FROM Encounter")
    fun getAll(): List<Encounter>

    @Query("SELECT * FROM Encounter where contact_id = :contactId")
    fun getEncountersByContactId(contactId: Int): List<Encounter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(encounter: Encounter): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg encounters: Encounter): List<Long>

    @Delete
    fun delete(encounter: Encounter)

    @Query("DELETE FROM Encounter where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(encounter: Encounter)
}