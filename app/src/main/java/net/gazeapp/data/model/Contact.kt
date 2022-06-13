package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

@Parcelize
@Entity(indices = [Index(value = [Contact.CONTACT_NAME])])
@SuppressWarnings(
    RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED,
    RoomWarnings.INDEX_FROM_EMBEDDED_FIELD_IS_DROPPED,
    RoomWarnings.INDEX_FROM_PARENT_IS_DROPPED
)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int,

    var isMe: Boolean = false,

    @ColumnInfo(name = CONTACT_NAME)
    var contactName: String?,

    @ColumnInfo(name = ADDITIONAL_INFO)
    var additionalInfo: String?,

    // Primary ID of Media table as the main pic of the contact
    @ColumnInfo(name = MAIN_PIC_ID)
    var mainPicId: Int = 0,

    var showMainPic: Boolean = true,

    @ColumnInfo(name = MET_IN_PERSON)
    var isMetInPerson: Boolean = false,

    @ColumnInfo(name = MET_IN_PERSON_DATE)
    @TypeConverters(RoomDateConverter::class)
    var metInPersonDate: Date?,

    @TypeConverters(RoomDateConverter::class)
    var created: Date?,

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date?,

    @TypeConverters(RoomDateConverter::class)
    @ColumnInfo(name = LAST_VIEWED)
    var lastViewed: Date?
) : Parcelable {
    constructor(name: String) : this(
        0,
        false,
        name,
        null,
        0,
        true,
        false,
        null,
        Date(),
        Date(),
        Date()
    )

    companion object {
        const val CONTACT_NAME = "contactName"
        const val ADDITIONAL_INFO = "additionalInfo"
        const val MAIN_PIC_ID = "mainPicFileId"
        const val MET_IN_PERSON = "metInPerson"
        const val MET_IN_PERSON_DATE = "metInPersonDate"
        const val LAST_VIEWED = "lastViewed"
    }
}