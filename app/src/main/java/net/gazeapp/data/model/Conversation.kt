package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

/**
 * Screenshots of chats/conversations from grindr, scruff etc.
 */
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
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Conversation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    @Ignore
    var isSelected: Boolean = false,

    // File name used in Gaze. If there are files with identical file name this field will create a unique file name here.
    @ColumnInfo(name = usedFilename)
    var usedFileName: String?,

    @ColumnInfo(name = originalFilename)
    var originalFileName: String?,

    // Full path + file name (internal storage)
    var fullPath: String?,

    @ColumnInfo(name = IS_IN_PRIVATE_GALLERY)
    var isInPrivateGallery: Boolean = false,

    var isXRated: Boolean = false,
    var caption: String?,

    // List of Labels i.e. grindr, scruff etc.
    var labels: String?,

    @TypeConverters(RoomDateConverter::class)
    var created: Date = Date(),

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date = Date(),

    var sortOrder: Int = 0
) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        false,
        null,
        null,
        "",
        false,
        false,
        null,
        null,
        Date(),
        Date(),
        0
    )

    companion object {
        const val MY_MEDIA_GALLERY_NUMBER = "myMediaGalleryNumber"
        const val IS_IN_PRIVATE_GALLERY = "isInPrivateGallery"
        const val originalFilename = "originalFileName"
        const val usedFilename = "usedFileName"
    }
}