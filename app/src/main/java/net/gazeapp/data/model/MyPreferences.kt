package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.converter.RoomDateConverter
import java.util.*

@Parcelize
@Entity
data class MyPreferences(
    // ID IS ALWAYS 1 IN THIS TABLE. ONLY ONE ENTRY IN THIS TABLE.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    var usePassword: Boolean = false,
    var useFingerprint: Boolean = false,
    var useMetricSystem: Boolean = true,
    var showMainPics: Boolean = true,
    var showNudePics: Boolean = false,
    var passwordMd5: String?,
    var isProUser: Boolean = false,
    var proUserJson: String?,

    @TypeConverters(RoomDateConverter::class)
    var proUserSince: Date?,

    var isAdsDisabled: Boolean = false,

    // If TRUE then prompt for fingerprint or PIN before displaying "Me" gallery one
    var isMeGalleryOneProtected: Boolean = false,

    // If TRUE then prompt for fingerprint or PIN before displaying "Me" gallery two
    var isMeGalleryTwoProtected: Boolean = false
) : Parcelable {
    constructor() : this(
        1,
        false,
        false,
        false,
        true,
        false,
        null,
        false,
        null,
        null,
        false,
        false,
        false
    )
}