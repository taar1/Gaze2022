package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Personal

@Parcelize
data class PersonalDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var birthdate: String?,
    var age: Int = 0,
    var isOut: Int = 0,
    var isOutTo: String?,
    var effeminate: Int = 0,
    var drinking: String?,
    var smoking: String?,
    var religion: String?,
    var relationshipStatus: String?,
    var politicalView: String?,
    var personal: Personal
) : Parcelable {
    constructor() : this(
        0,
        0,
        null,
        0,
        2,
        null,
        0,
        null,
        null,
        null,
        null,
        null,
        Personal(0)
    )

}