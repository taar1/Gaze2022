package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Body

@Dao
interface BodyDao {
    @Query("SELECT * FROM Body where contact_id = :contactId")
    fun getBodiesByContactId(contactId: Int): List<Body>

    @Query("SELECT * FROM Body where contact_id = :contactId LIMIT 1")
    fun getBody(contactId: Int): Body

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(body: Body): Long

    @Delete
    fun delete(Body: Body)

    @Update
    fun update(Body: Body)
}