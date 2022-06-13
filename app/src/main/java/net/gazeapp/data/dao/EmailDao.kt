package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Email

@Dao
interface EmailDao {
    @Query("SELECT * FROM Email")
    fun getAll(): List<Email>

    @Query("SELECT * FROM Email where contact_id = :contactId")
    fun getEmailsByContactId(contactId: Int): List<Email>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(email: Email): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg emails: Email): List<Long>

    @Delete
    fun delete(email: Email)

    @Query("DELETE FROM Email where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(email: Email)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(emails: List<Email>)
}