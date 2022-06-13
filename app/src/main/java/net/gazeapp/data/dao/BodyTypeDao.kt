package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.BodyType

@Dao
interface BodyTypeDao {
    @Query("SELECT * FROM BodyType")
    fun getAll(): List<BodyType>

    @Query("SELECT * FROM BodyType where contact_id = :contactId")
    fun getBodyTypes(contactId: Int): List<BodyType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bodyType: BodyType): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg bodyTypes: BodyType): List<Long>

    @Delete
    fun delete(BodyType: BodyType)

    @Update
    fun update(BodyType: BodyType)

    @Query("DELETE FROM BodyType where contact_id = :contactId")
    fun deleteAll(contactId: Int)
}