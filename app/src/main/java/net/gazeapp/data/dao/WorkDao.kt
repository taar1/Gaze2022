package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Work

@Dao
interface WorkDao {
    @Query("SELECT * FROM Work WHERE contact_id = :contactId")
    fun getWorksByContactId(contactId: Int): List<Work>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(work: Work): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg workEntities: Work): List<Long>

    @Delete
    fun delete(work: Work)

    @Delete
    fun delete(vararg work: Work)

    @Query("DELETE FROM Work where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(work: Work)
}