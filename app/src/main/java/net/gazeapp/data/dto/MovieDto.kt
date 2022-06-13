package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Movie

@Parcelize
data class MovieDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var movieStr: String?,
    var url: String?,
    var likesIt: Boolean = false,
    var movie: Movie

) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        true,
        Movie(0)
    )
}