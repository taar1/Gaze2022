package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Nickname

@Parcelize
data class NicknameDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var nicknameStr: String?,
    var nickname: Nickname
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        Nickname(0)
    )

}