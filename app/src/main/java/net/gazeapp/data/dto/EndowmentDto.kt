package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Endowment

@Parcelize
data class EndowmentDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var length: String?,
    var diameter: String?,
    var girth: String?,
    var isCut: String?,
    var isCutUnknownCheckbox: Boolean,
    var feelsLike: String?,
    var endowment: Endowment
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        false,
        "",
        Endowment(0)
    )
}