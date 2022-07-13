package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.MediaTag

@Dao
interface MediaTagDao {
    @Query("SELECT * FROM MediaTag")
    fun getAll(): List<MediaTag>

    @Query("SELECT * FROM MediaTag WHERE ID = :id")
    fun getTag(id: Int): MediaTag

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(mediaTag: MediaTag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg mediaTags: MediaTag): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(mediaTags: List<MediaTag>)

    @Delete
    fun delete(mediaTag: MediaTag)

    @Delete
    fun delete(vararg mediaTag: MediaTag)

    @Query("DELETE FROM MediaTag where ID = :id")
    fun deleteTag(id: Int)

    @Update
    fun update(mediaTag: MediaTag)

}