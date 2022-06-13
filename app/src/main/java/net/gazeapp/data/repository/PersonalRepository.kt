package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Personal

class PersonalRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "PersonalRepository"
    }

    suspend fun delete(personal: Personal) {
        withContext(Dispatchers.IO) {
            database.personalDao.delete(personal)
        }
    }

    suspend fun update(personal: Personal) {
        withContext(Dispatchers.IO) {
            database.personalDao.update(personal)
        }
    }

    suspend fun insert(personal: Personal) {
        withContext(Dispatchers.IO) {
            database.personalDao.insert(personal)
        }
    }
}