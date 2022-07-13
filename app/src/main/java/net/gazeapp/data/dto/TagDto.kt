package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.MediaTag

/**
 * Tags are used to tag media files, i.e. nudes, xxx, facepic etc.
 */
@Parcelize
data class TagDto(
    var id: Int = 0,
    var tagStr: String?,
    var added: String?,
    var lastMod: String?,
    var isSelected: Boolean = false,
    var mediaTag: MediaTag
) : Parcelable {
    constructor(id: Int) : this(
        id,
        "",
        "",
        "",
        false,
        MediaTag(0)
    )
}