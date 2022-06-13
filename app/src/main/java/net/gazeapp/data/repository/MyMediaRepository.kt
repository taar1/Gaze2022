package net.gazeapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.MyMedia

class MyMediaRepository(val database: GazeDatabase) {

    private val TAG = "MyMediaRepository"

    var myMediaList: List<MyMedia> = listOf()

    suspend fun getMyMedia(galleryNumber: Int, orderBy: String) {
        withContext(Dispatchers.IO) {
            myMediaList =
                database.myMediaDao.getFromGalleryOrderBy(galleryNumber, orderBy)
        }
    }

    suspend fun insertMedia(myMedia: MyMedia) {
        Log.d(TAG, "XXXXX insertMedia: 1")
        withContext(Dispatchers.IO) {
            database.myMediaDao.insert(myMedia)
        }
    }


    suspend fun insertMediaList(mediaList: List<MyMedia>) {
        Log.d(TAG, "XXXXX insertMediaList: ")

        withContext(Dispatchers.IO) {
            database.myMediaDao.insertAll(media = mediaList.toTypedArray())
        }
    }

}