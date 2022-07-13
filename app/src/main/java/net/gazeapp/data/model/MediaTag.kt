package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

/**
 * MediaTags are used to tag media files, i.e. nudes, xxx, facepic etc.
 */
@Parcelize
@Entity
data class MediaTag(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,
    var tag: String = "",

    @TypeConverters(RoomDateConverter::class)
    var added: Date = Date(),

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date = Date(),

    @Ignore
    var isSelected: Boolean = false
) : Parcelable {
    constructor() : this(
        0,
        "",
        Date(),
        Date(),
        false
    )
}