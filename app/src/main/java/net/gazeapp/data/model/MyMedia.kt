package net.gazeapp.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import net.gazeapp.data.converter.RoomUriConverter
import java.util.*

/**
 * Meta data of media in the Me-Tab.
 */
@Parcelize
@Entity
data class MyMedia(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @Ignore
    var isSelected: Boolean = false,

    // File name used in Gaze. If there are files with identical file name this field will create a unique file name here.
    var usedFileName: String,

    var originalFileName: String,

    var path: String,

    // Full path + file name (internal storage)
    var fullPath: String,

    // audio, video, image
    var fileType: String = "image",

    var myMediaGalleryNumber: Int = 1,

    var isXRated: Boolean = false,
    var caption: String?,

    // just free style String for adding labels (comma separated)
    var labels: String?,

    @TypeConverters(RoomDateConverter::class)
    var created: Date = Date(),

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date = Date(),

    var sortOrder: Int = 0,

    @TypeConverters(RoomUriConverter::class)
    var uri: Uri?
) : Parcelable {
    constructor() : this(
        0,
        false,
        "",
        "",
        "",
        "",
        "image",
        1,
        false,
        null,
        null,
        Date(),
        Date(),
        0,
        null
    )
}