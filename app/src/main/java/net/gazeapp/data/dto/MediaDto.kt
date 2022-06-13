package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Media

@Parcelize
data class MediaDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var isSelected: Boolean = false,
    var usedFileName: String?,
    var originalFileName: String?,
    var path: String?,
    var fullPath: String?,
    var fileType: String?,
    var isInPrivateGallery: Boolean = false,
    var isXRated: Boolean = false,
    var caption: String?,
    var labels: String?,
    var created: String?,
    var lastMod: String?,
    var sortOrder: Int = 0,
    var uri: String?,
    var media: Media
) : Parcelable {
    constructor() : this(
        0,
        0,
        false,
        "",
        "",
        "",
        "",
        "image",
        false,
        false,
        "",
        "",
        "",
        "",
        0,
        "",
        Media(0)
    )
}