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
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Personal(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    @TypeConverters(RoomDateConverter::class)
    var birthdate: Date?,

    var age: Int = 0,

    // 0=no, 1=yes, 2=unknown
    var isOut: Int = 0,

    // to whom is he out? family only? friends only? etc.
    var isOutTo: String?,

    // 1=masc, 100=fem, 0=unknown
    var effeminate: Int = 0,

    // Never, socially, alcoholic
    var drinking: String?,

    // Never, occasionally, chain smoker
    var smoking: String?,

    var religion: String?,
    var relationshipStatus: String?,
    var politicalView: String?
) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        null,
        0,
        2,
        null,
        0,
        null,
        null,
        null,
        null,
        null
    )

}