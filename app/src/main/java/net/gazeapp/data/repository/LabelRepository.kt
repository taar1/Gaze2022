package net.gazeapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.GazeApplication
import net.gazeapp.R
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Label
import java.util.*

class LabelRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "LabelRepository"
    }

    var labelList: List<Label> = listOf()

    suspend fun getLabels() {
        withContext(Dispatchers.IO) {
            labelList =
                database.labelDao.getAll()
        }
    }

    suspend fun insertLabel(label: Label) {
        Log.d(TAG, "XXXXX insertLabel: 1")
        withContext(Dispatchers.IO) {
            database.labelDao.insert(label)
        }
    }


    suspend fun insertLabels() {
        Log.d(TAG, "XXXXX insertLabels: x")
        withContext(Dispatchers.IO) {
            database.labelDao.insertAll(
                mutableListOf(
                    Label(
                        0,
                        GazeApplication.appContext.getString(R.string.holidays),
                        Date(),
                        Date(),
                        false
                    ),
                    Label(
                        0,
                        GazeApplication.appContext.getString(R.string.mykonos),
                        Date(),
                        Date(),
                        false
                    ),
                    Label(
                        0,
                        GazeApplication.appContext.getString(R.string.from_grindr),
                        Date(),
                        Date(),
                        false
                    )
                )
            )
        }
    }


}