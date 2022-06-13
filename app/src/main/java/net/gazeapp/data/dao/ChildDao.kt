package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Child

@Dao
interface ChildDao {
    @Query("SELECT * FROM Child")
    fun getALl(): List<Child>

    @Query("SELECT * FROM Child where contact_id = :contactId")
    fun getChildrenByContactId(contactId: String): List<Child>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(child: Child): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg children: Child): List<Long>

    @Delete
    fun delete(child: Child)

    @Update
    fun update(child: Child)

    @Query("DELETE FROM Child where contact_id = :contactId")
    fun deleteAll(contactId: Int)
}