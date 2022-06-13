package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

@Parcelize
@Entity(indices = [Index(value = ["label"], unique = true)])
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Label(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,
    var label: String = "",

    @TypeConverters(RoomDateConverter::class)
    var added: Date = Date(),

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date = Date(),

    @Ignore
    var isSelected: Boolean = false
) : Parcelable {

    constructor(labelText: String) : this(
        0,
        labelText,
        Date(),
        Date(),
        false
    )

}