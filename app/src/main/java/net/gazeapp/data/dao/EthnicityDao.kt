package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Ethnicity

@Dao
interface EthnicityDao {
    @Query("SELECT * FROM Ethnicity")
    fun getAll(): List<Ethnicity>

    @Query("SELECT * FROM Ethnicity where contact_id = :contactId")
    fun getEthnicities(contactId: Int): List<Ethnicity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ethnicity: Ethnicity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ethnicites: Ethnicity): List<Long>

    @Delete
    fun delete(ethnicity: Ethnicity)

    @Query("DELETE FROM Ethnicity where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(ethnicity: Ethnicity)
}