package net.gazeapp.data.dto

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.MyMedia

/**
 * Meta data of media in the Me-Tab.
 */
@Parcelize
data class MyMediaDto(
    var id: Int = 0,
    var isSelected: Boolean = false,
    var usedFileName: String?,
    var originalFileName: String?,
    var path: String?,
    var fullPath: String?,
    var fileType: String?,
    var myMediaGalleryNumber: Int = 1,
    var isXRated: Boolean = false,
    var caption: String?,
    var labels: String?,
    var created: String?,
    var lastMod: String?,
    var sortOrder: Int = 0,
    var uri: Uri?,
    var myMedia: MyMedia
) : Parcelable {
    constructor() : this(
        0,
        false,
        null,
        null,
        null,
        null,
        null,
        1,
        false,
        null,
        null,
        null,
        null,
        0,
        null,
        MyMedia()
    )
}