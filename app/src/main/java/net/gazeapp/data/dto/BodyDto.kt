package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Body

@Parcelize
data class BodyDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var weight: String?, // grams
    var height: String?, // cm
    var hairColor: String?,
    var hairStyle: String?,
    var eyeColor: String?,
    var bodyHair: String?,
    var gender: String?,
    var body: Body
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        Body(0)
    )
}