package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Phone

class PhoneRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "PhoneRepository"
    }

    suspend fun delete(phone: Phone) {
        withContext(Dispatchers.IO) {
            database.phoneDao.delete(phone)
        }
    }

    suspend fun update(phone: Phone) {
        withContext(Dispatchers.IO) {
            database.phoneDao.update(phone)
        }
    }

    suspend fun insert(phone: Phone) {
        withContext(Dispatchers.IO) {
            database.phoneDao.insert(phone)
        }
    }
}