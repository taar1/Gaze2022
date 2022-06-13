package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Note

@Parcelize
data class NoteDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var noteStr: String?,
    var noteTextColor: Int = 0, // 0 = black
    var noteBackgroundColor: Int = 0, // 0 = white
    var textSizeSp: Int = 0, // 0 = default text size
    var created: String?,
    var lastMod: String?,
    var note: Note
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        0,
        0,
        0,
        "",
        "",
        Note(0)
    )
}