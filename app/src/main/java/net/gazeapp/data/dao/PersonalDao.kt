package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Personal

@Dao
interface PersonalDao {
    @Query("SELECT * FROM Personal where contact_id = :contactId LIMIT 1")
    fun getPersonal(contactId: Int): Personal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personal: Personal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg personalEntities: Personal): List<Long>

    @Delete
    fun delete(personal: Personal)

    @Delete
    fun delete(vararg personal: Personal)

    @Update
    fun update(personal: Personal)
}