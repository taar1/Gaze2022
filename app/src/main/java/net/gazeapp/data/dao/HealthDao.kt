package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Health

@Dao
interface HealthDao {
    @Query("SELECT * FROM Health")
    fun getAll(): List<Health>

    @Query("SELECT * FROM Health where contact_id = :contactId LIMIT 1")
    fun getHealthsByContactId(contactId: Int): Health

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(health: Health): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg healths: Health): List<Long>

    @Delete
    fun delete(health: Health)

    @Query("DELETE FROM Health where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(health: Health)
}