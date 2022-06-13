package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Nickname

@Dao
interface NicknameDao {
    @Query("SELECT * FROM Nickname where contact_id = :contactId ORDER BY nickname ASC")
    fun getNicknamesByContactId(contactId: Int): List<Nickname>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nickname: Nickname): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg nicknames: Nickname): List<Long>

    @Delete
    fun delete(nickname: Nickname)

    @Delete
    fun delete(vararg nicknames: Nickname)

    @Query("DELETE FROM Nickname where contact_id = :contactId")
    fun deleteAll(contactId: Int)

    @Update
    fun update(nickname: Nickname)
}