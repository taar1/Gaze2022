package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Hobby

@Parcelize
data class HobbyDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var hobbyId: Int = 0,
    var hobbyStr: String?,
    var hobby: Hobby
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        Hobby(0)
    )
}