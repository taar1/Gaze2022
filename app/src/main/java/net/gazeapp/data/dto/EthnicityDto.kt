package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Ethnicity

@Parcelize
data class EthnicityDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var ethnicityId: Int,
    var ethnicityStr: String,
    var isSelected: Boolean,
    var ethnicity: Ethnicity?
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        false,
        Ethnicity(0)
    )
}