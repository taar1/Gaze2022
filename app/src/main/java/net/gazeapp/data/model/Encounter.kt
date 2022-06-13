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
data class Encounter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    @TypeConverters(RoomDateConverter::class)
    var meetDate: Date = Date(),
    var meetLocation: String?,
    var myRole: String?,
    var hisRole: String?,

    var isSafeSex: Boolean = true,
    var sureAboutSafeSex: Boolean = true,

    var myLoadWentTo: String?,
    var hisLoadWentTo: String?,
    var remarks: String?,
    var longitude: String?,
    var latitude: String?,
    var googleMapsLink: String?,

    // from 1-10
    var rating: Int = 0,

    @TypeConverters(RoomDateConverter::class)
    var created: Date = Date(),

    @TypeConverters(RoomDateConverter::class)
    var lastMod: Date = Date()

) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        Date(),
        null,
        null,
        null,
        true,
        true,
        null,
        null,
        null,
        null,
        null,
        null,
        0,
        Date(),
        Date()
    )

}