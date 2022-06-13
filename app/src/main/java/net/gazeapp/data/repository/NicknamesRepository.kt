package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Nickname

class NicknamesRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "NicknamesRepository"
    }

    suspend fun delete(nickname: Nickname) {
        withContext(Dispatchers.IO) {
            database.nicknameDao.delete(nickname)
        }
    }

    suspend fun update(nickname: Nickname) {
        withContext(Dispatchers.IO) {
            database.nicknameDao.update(nickname)
        }
    }

    suspend fun insert(nickname: Nickname) {
        withContext(Dispatchers.IO) {
            database.nicknameDao.insert(nickname)
        }
    }
}