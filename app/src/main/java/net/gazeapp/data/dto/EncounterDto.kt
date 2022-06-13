package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Encounter

@Parcelize
data class EncounterDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var meetDate: String?,
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
    var rating: Int = 0,
    var created: String?,
    var lastMod: String?,
    var encounter: Encounter

) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
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
        "",
        "",
        Encounter(0)
    )
}