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
data class Health(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    var hiv: String?,
    @TypeConverters(RoomDateConverter::class)
    var hivTestDate: Date?,

    var hcv: String?, // Hepatitis
    @TypeConverters(RoomDateConverter::class)
    var hcvVaccinationDate: Date?,

    var hsv1: String?,
    var hsv2: String?,

    var hpv: String?,
    @TypeConverters(RoomDateConverter::class)
    var hpvVaccinationDate: Date?,

    var hadCovid19: Int? = 2, // 0=no, 1=yes, 2=unknown
    @TypeConverters(RoomDateConverter::class)
    var covid19InfectionDate: Date?,

    var isCovid19Vaccinated: Int? = 2, // 0=no, 1=yes, 2=unknown

    @TypeConverters(RoomDateConverter::class)
    var covid19VaccinationDate: Date?,
    var covid19VaccineBrand: String?

) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}