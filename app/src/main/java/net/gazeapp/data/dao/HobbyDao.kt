package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Hobby

@Dao
interface HobbyDao {
    @Query("SELECT * from Hobby ORDER BY hobby ASC")
    fun getAll(): List<Hobby>

    @Query("SELECT * FROM Hobby where contact_id = :contactId ORDER BY hobby ASC")
    fun getHobbies(contactId: Int): List<Hobby>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hobby: Hobby): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg hobbies: Hobby): List<Long>

    @Delete
    fun delete(hobby: Hobby)

    @Update
    fun update(hobby: Hobby)
}