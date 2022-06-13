package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

@Parcelize
@Entity(
    indices = [Index(PersistentObject.CONTACT_ID_FIELD_NAME)],
    foreignKeys = [ForeignKey(
        entity = Contact::class,
        parentColumns = [PersistentObject.ID],
        childColumns = [PersistentObject.CONTACT_ID_FIELD_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,
    var note: String?,
    var noteTextColor: Int = 0, // 0 = black
    var noteBackgroundColor: Int = 0, // 0 = white
    var textSizeSp: Int = 0, // 0 = default text size

    @TypeConverters(RoomDateConverter::class)
    var created: Date?,

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date?
) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        null,
        0,
        0,
        0,
        Date(),
        Date()
    )
}