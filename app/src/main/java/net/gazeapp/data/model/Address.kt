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
data class Address(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,
    var addressType: String?,

    @ColumnInfo(name = ADDRESS)
    var address: String?
) : Parcelable {

    constructor(contactId: Int) : this(
        0,
        contactId,
        null,
        null
    )

    companion object {
        const val ADDRESS = "address"
    }
}