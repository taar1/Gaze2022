package net.gazeapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GazeImage(var id: Long, var name: String, var path: String, var isSelected: Boolean) :
    Parcelable