package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Note

class NotesRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "NotesRepository"
    }

    suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            database.noteDao.delete(note)
        }
    }

    suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            database.noteDao.update(note)
        }
    }

    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            database.noteDao.insert(note)
        }
    }
}