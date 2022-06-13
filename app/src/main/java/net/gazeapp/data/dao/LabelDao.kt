package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Label

@Dao
interface LabelDao {
    @Query("SELECT * FROM Label ORDER BY label COLLATE NOCASE")
    fun getAll(): List<Label>

    @Query("SELECT * FROM Label WHERE id = :id")
    operator fun get(id: Int): Label

    @Query("SELECT * FROM Label WHERE label = :label")
    operator fun get(label: String): Label

    @Query("SELECT * FROM Label WHERE id IN(:labelIds)")
    operator fun get(labelIds: List<Int>): List<Label>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(label: Label): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg labels: Label): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(labels: List<Label>)

    @Delete
    fun delete(label: Label): Int

    @Update
    fun update(label: Label)


}