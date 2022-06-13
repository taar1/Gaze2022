package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Media

class MediaRepository(val database: GazeDatabase) {

    var mediaList: List<Media> = listOf()

    suspend fun getMedia(contactId: Int, isInPrivateGallery: Boolean) {
        withContext(Dispatchers.IO) {
            mediaList =
                database.mediaDao.getMedia(contactId, isInPrivateGallery)
        }
    }


}