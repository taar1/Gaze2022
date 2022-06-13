package net.gazeapp.data.converter

import android.net.Uri
import androidx.room.TypeConverter

class RoomUriConverter {

    @TypeConverter
    fun toUri(uri: String?): Uri {
        return Uri.parse(uri)
    }

    @TypeConverter
    fun toString(uri: Uri): String {
        return uri.toString()
    }
}