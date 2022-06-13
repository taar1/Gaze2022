package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Address

class AddressRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "AddressRepository"
    }

    suspend fun delete(address: Address) {
        withContext(Dispatchers.IO) {
            database.addressDao.delete(address)
        }
    }

    suspend fun update(address: Address) {
        withContext(Dispatchers.IO) {
            database.addressDao.update(address)
        }
    }

    suspend fun insert(address: Address) {
        withContext(Dispatchers.IO) {
            database.addressDao.insert(address)
        }
    }
}