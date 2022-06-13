package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Tag

@Dao
interface TagDao {
    @Query("SELECT * FROM Tag")
    fun getAll(): List<Tag>

    @Query("SELECT * FROM Tag WHERE ID = :id")
    fun getTag(id: Int): Tag

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg tags: Tag): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(tags: List<Tag>)

    @Delete
    fun delete(tag: Tag)

    @Delete
    fun delete(vararg tag: Tag)

    @Query("DELETE FROM Tag where ID = :id")
    fun deleteTag(id: Int)

    @Update
    fun update(tag: Tag)

}