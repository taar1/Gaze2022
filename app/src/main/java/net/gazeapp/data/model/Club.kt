package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

/**
 * Clubs such as sport clubs or chess club etc.
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
data class Club(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,
    var clubName: String?,
    var address: String?,
    var phoneNumber: String?,
    var email: String?,
    var url: String?
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        null
    )

    constructor(contactId: Int) : this(
        0,
        contactId,
        "",
        "",
        "",
        "",
        null
    )
}