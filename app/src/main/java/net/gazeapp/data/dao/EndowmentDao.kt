package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Endowment

@Dao
interface EndowmentDao {
    @Query("SELECT * FROM Endowment")
    fun getAll(): List<Endowment>

    @Query("SELECT * FROM Endowment where contact_id = :contactId LIMIT 1")
    fun getEndowmentByContactId(contactId: Int): Endowment

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(endowment: Endowment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg endowments: Endowment): List<Long>

    @Delete
    fun delete(endowment: Endowment)

    @Query("DELETE FROM Endowment where contact_id = :contactId")
    fun deleteEndowments(contactId: Int)

    @Update
    fun update(endowment: Endowment)
}