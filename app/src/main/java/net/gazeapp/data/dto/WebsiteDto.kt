package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Website

@Parcelize
data class WebsiteDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var websiteStr: String = "",
    var websiteType: String?,
    var description: String?,
    var website: Website
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        null,
        null,
        Website(0)
    )
}