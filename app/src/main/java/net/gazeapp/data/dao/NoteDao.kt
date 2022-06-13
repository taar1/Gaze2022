package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note where contact_id = :contactId ORDER BY note ASC")
    fun getNotes(contactId: Int): List<Note>

    @Query("SELECT * FROM Note where contact_id = :contactId ORDER BY note ASC LIMIT 1")
    fun getNote(contactId: Int): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: Note): List<Long>

    @Delete
    fun delete(note: Note)

    @Delete
    fun delete(vararg note: Note)

    @Update
    fun update(note: Note)
}