package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.BodyType

@Parcelize
data class BodyTypeDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var bodytypeId: Int,
    var bodyTypeStr: String?,
    var isSelected: Boolean,
    var bodyType: BodyType
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        false,
        BodyType(0)
    )
}