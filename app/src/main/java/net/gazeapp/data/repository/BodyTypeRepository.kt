package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.BodyType

class BodyTypeRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "BodyTypeRepository"
    }

    suspend fun delete(bodyType: BodyType) {
        withContext(Dispatchers.IO) {
            database.bodyTypeDao.delete(bodyType)
        }
    }

    suspend fun update(bodyType: BodyType) {
        withContext(Dispatchers.IO) {
            database.bodyTypeDao.update(bodyType)
        }
    }

    suspend fun insert(bodyType: BodyType) {
        withContext(Dispatchers.IO) {
            database.bodyTypeDao.insert(bodyType)
        }
    }
}