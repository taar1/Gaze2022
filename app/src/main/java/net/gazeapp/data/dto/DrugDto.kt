package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Drug

@Parcelize
data class DrugDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var drugId: Int = 0,
    var drugStr: String?,
    var drug: Drug
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        Drug(0)
    )
}