package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

@Parcelize
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    var achievement: String,
    var achievementDescription: String?,

    @TypeConverters(RoomDateConverter::class)
    var startDate: Date?,

    @TypeConverters(RoomDateConverter::class)
    var completeDate: Date?

) : Parcelable {

    constructor(achievement: String) : this(
        0,
        achievement,
        null,
        Date(),
        Date()
    )
}