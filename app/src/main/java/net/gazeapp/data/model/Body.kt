package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

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
@SuppressWarnings(
    RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED,
    RoomWarnings.INDEX_FROM_EMBEDDED_FIELD_IS_DROPPED,
    RoomWarnings.INDEX_FROM_PARENT_IS_DROPPED
)
data class Body(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    // in grams
    var weight: Int = 0,

    // in cm
    var height: Int = 0,

    var hairColor: String?,
    var hairStyle: String?,
    var eyeColor: String?,
    var bodyHair: String?,
    var gender: String?
) : Parcelable {

    constructor() : this(
        0,
        0,
        0,
        0,
        null, null, null, null, null
    )

    constructor(contactId: Int) : this(
        0,
        contactId,
        0,
        0,
        null,
        null,
        null,
        null,
        null
    )
}