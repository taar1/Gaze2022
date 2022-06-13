package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Address

@Parcelize
data class AddressDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var addressType: String?,
    var addressStr: String?,
    var address: Address
) : Parcelable {

    constructor() : this(
        0,
        0,
        null,
        null,
        Address(0)
    )
}