package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Club

@Parcelize
data class ClubDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var clubName: String?,
    var address: String?,
    var phoneNumber: String?,
    var email: String?,
    var url: String?,
    var club: Club
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        "",
        Club(0)
    )
}