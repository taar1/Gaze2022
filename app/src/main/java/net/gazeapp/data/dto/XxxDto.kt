package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Xxx


@Parcelize
data class XxxDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var sexualOrientation: String?,
    var sexRole: String?,                       // top, bottom etc.
    var safeSex: String?,
    var takesLoadsUpTheBum: String?,            // 0=no, 1=yes, 2=unknown, 3=blank/hidden
    var swallowsLoads: String?,                 // 0=no, 1=yes, 2=unknown, 3=blank/hidden
    var prefCumGiveDestination: String?,        // ass, face etc. TODO
    var prefCumReceiveDestination: String?,     // ass, face etc. TODO
    var sexperience: Int?,                    // 0=terrible, 10=pronstar
    var xxx: Xxx
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        Xxx(0)
    )
}