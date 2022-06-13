package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Child

@Parcelize
data class ChildDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var name: String?,
    var pronoun: String?,
    var birthdate: String?,
    var child: Child
) : Parcelable {
    constructor() : this(
        0,
        0,
        null,
        null,
        null,
        Child(0)
    )
}