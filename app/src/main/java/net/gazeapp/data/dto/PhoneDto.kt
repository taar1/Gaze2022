package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Phone

@Parcelize
data class PhoneDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var phoneNumberType: String?,
    var phoneNumber: String?,
    var countryCode: String?,
    var phone: Phone
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        null,
        Phone(0)
    )
}