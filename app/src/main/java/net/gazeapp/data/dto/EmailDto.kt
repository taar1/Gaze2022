package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Email

@Parcelize
data class EmailDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var emailType: String?,
    var emailStr: String?,
    var email: Email
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        Email(0)
    )
}