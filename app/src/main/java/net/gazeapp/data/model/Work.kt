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
data class Work(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    @ColumnInfo(name = PROFESSION)
    var profession: String?,

    @ColumnInfo(name = EMPLOYER)
    var employer: String?,

    @ColumnInfo(name = EMPLOYER_ADDRESS)
    var employerAddress: String?,

    @TypeConverters(RoomDateConverter::class)
    var started: Date?,

    @TypeConverters(RoomDateConverter::class)
    var ended: Date?,

    @ColumnInfo(name = PHONE)
    var phone: String?,
    var fax: String?,

    @ColumnInfo(name = EMAIL)
    var email: String?,

    var salary: Int = 0,
    var currency: String?,

    // weekly, monthly, annually
    var frequency: String?,
    var notes: String?

) : Parcelable {
    constructor(contactId: Int) : this(
        0, contactId, null, null, null, null, null, null, null, null, 0, null, null, null
    )

    companion object {
        const val EMPLOYER = "employer"
        const val PROFESSION = "profession"
        const val EMPLOYER_ADDRESS = "employerAddress"
        const val PHONE = "phone"
        const val EMAIL = "email"
    }
}