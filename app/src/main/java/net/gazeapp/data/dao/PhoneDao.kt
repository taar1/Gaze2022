package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Phone

@Dao
interface PhoneDao {
    @Query("SELECT * FROM Phone where contact_id = :contactId")
    fun getPhonesByContactId(contactId: Int): List<Phone>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(phone: Phone): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg phones: Phone): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(phones: List<Phone>): List<Long>

    @Delete
    fun delete(phone: Phone)

    @Delete
    fun delete(vararg phone: Phone)

    @Query("DELETE FROM Phone where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(phone: Phone)
}