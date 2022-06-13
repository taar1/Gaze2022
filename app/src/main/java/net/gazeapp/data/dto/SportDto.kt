package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Sport

@Parcelize
data class SportDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var sportId: Int = 0,
    var sportStr: String?,
    var isLikesIt: Boolean = true,
    var sport: Sport
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        true,
        Sport(0)
    )
}