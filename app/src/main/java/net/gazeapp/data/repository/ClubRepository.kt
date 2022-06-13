package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Club

class ClubRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "ClubRepository"
    }

    suspend fun delete(club: Club) {
        withContext(Dispatchers.IO) {
            database.clubDao.delete(club)
        }
    }

    suspend fun update(club: Club) {
        withContext(Dispatchers.IO) {
            database.clubDao.update(club)
        }
    }

    suspend fun insert(club: Club) {
        withContext(Dispatchers.IO) {
            database.clubDao.insert(club)
        }
    }
}