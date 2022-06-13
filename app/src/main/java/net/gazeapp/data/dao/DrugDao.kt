package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Drug

@Dao
interface DrugDao {
    @Query("SELECT * FROM Drug")
    fun getAll(): List<Drug>

    @Query("SELECT * FROM Drug where contact_id = :contactId")
    fun getDrugsByContactId(contactId: Int): List<Drug>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drug: Drug): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg drugs: Drug): List<Long>

    @Delete
    fun delete(drug: Drug)

    @Query("DELETE FROM Drug where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(drug: Drug)
}