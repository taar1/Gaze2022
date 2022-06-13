package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Label

@Parcelize
data class LabelDto(
    var id: Int = 0,
    var labelStr: String?,
    var added: String?,
    var lastMod: String?,
    var isSelected: Boolean = false,
    var label: Label
) : Parcelable {
    constructor() : this(
        0,
        "",
        "",
        "",
        false,
        Label(0)
    )

}