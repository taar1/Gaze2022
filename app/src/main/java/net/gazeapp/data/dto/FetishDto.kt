package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Fetish

@Parcelize
data class FetishDto(
    var id: Int,
    var contactId: Int,
    var fetishId: Int,
    var fetishStr: String,
    var isSelected: Boolean,
    var fetish: Fetish?
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        false,
        Fetish(0)
    )
}