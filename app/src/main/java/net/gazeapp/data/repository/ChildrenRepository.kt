package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Child

class ChildrenRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "ChildrenRepository"
    }

    suspend fun delete(child: Child) {
        withContext(Dispatchers.IO) {
            database.childDao.delete(child)
        }
    }

    suspend fun update(child: Child) {
        withContext(Dispatchers.IO) {
            database.childDao.update(child)
        }
    }

    suspend fun insert(child: Child) {
        withContext(Dispatchers.IO) {
            database.childDao.insert(child)
        }
    }
}