package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

/**
 * Meta data of contact media (other people, not to confuse with media of yourself)
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
data class Media(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    @Ignore
    var isSelected: Boolean = false,

    // File name used in Gaze. If there are files with identical file name this field will create a unique file name here.
    @ColumnInfo(name = usedFilename)
    var usedFileName: String,

    @ColumnInfo(name = originalFilename)
    var originalFileName: String,

    var path: String,

    // Full path + file name (internal storage)
    var fullPath: String,

    // audio, video, image
    var fileType: String = "image",

    @ColumnInfo(name = IS_IN_PRIVATE_GALLERY)
    var isInPrivateGallery: Boolean = false,

    var isXRated: Boolean = false,
    var caption: String?,

    // String of freestyle labels i.e. holidays, beach, nude. There is also a TAG table.
    // TODO: the user can edit the TAG table with tags and then select them from a drop down list for each media file.
    // TODO: replace the label string with the TAG list eventually...
    var labels: String?,

    @TypeConverters(RoomDateConverter::class)
    var created: Date = Date(),

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date = Date(),

    var sortOrder: Int = 0,

    var uri: String?
) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        false,
        "",
        "",
        "",
        "",
        "image",
        false,
        false,
        null,
        null,
        Date(),
        Date(),
        0,
        null
    )

    companion object {
        const val IS_IN_PRIVATE_GALLERY = "isInPrivateGallery"
        const val originalFilename = "originalFileName"
        const val usedFilename = "usedFileName"
    }
}