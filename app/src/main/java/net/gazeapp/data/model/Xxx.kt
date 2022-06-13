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
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Xxx(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,
    var sexualOrientation: String?,
    var sexRole: String?,                       // top, bottom etc.
    var safeSex: String?,
    var takesLoadsUpTheBum: Int = 0,            // 0=no, 1=yes, 2=unknown, 3=blank/hidden
    var swallowsLoads: Int = 0,                 // 0=no, 1=yes, 2=unknown, 3=blank/hidden
    var prefCumGiveDestination: String?,        // ass, face etc.
    var prefCumReceiveDestination: String?,     // ass, face etc.
    var sexperience: Int = 0                    // 0=terrible, 10=pronstar
) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        null,
        null,
        null,
        3,
        3,
        null,
        null,
        0
    )
}