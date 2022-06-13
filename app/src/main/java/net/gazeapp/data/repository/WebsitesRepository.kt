package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Website

class WebsitesRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "WebsitesRepository"
    }

    suspend fun delete(website: Website) {
        withContext(Dispatchers.IO) {
            database.websiteDao.delete(website)
        }
    }

    suspend fun update(website: Website) {
        withContext(Dispatchers.IO) {
            database.websiteDao.update(website)
        }
    }

    suspend fun insert(website: Website) {
        withContext(Dispatchers.IO) {
            database.websiteDao.insert(website)
        }
    }
}