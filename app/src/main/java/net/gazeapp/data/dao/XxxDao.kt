package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Xxx

@Dao
interface XxxDao {
    @Query("SELECT * FROM Xxx where contact_id = :contactId")
    fun getXxxs(contactId: Int): List<Xxx>

    @Query("SELECT * FROM Xxx where contact_id = :contactId LIMIT 1")
    fun getXxx(contactId: Int): Xxx

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(xxx: Xxx): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg xxx: Xxx): List<Long>

    @Delete
    fun delete(xxx: Xxx)

    @Delete
    fun delete(vararg xxx: Xxx)

    @Query("DELETE FROM Xxx where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(xxx: Xxx)
}