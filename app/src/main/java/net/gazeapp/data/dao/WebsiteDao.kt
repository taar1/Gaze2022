package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Website

@Dao
interface WebsiteDao {
    @Query("SELECT * FROM Website where contact_id = :contactId")
    fun getWebsitesByContactId(contactId: Int): List<Website>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(website: Website): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg websites: Website): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(websites: List<Website>): List<Long>

    @Delete
    fun delete(website: Website)

    @Delete
    fun delete(vararg website: Website)

    @Query("DELETE FROM Website where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(website: Website)
}