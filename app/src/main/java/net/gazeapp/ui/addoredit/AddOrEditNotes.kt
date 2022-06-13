package net.gazeapp.ui.addoredit

import androidx.core.view.isVisible
import net.gazeapp.data.dto.NoteDto

class AddOrEditNotes(
    val f: AddOrEditContactFragment
) {
    fun displayNotes(notesDto: NoteDto) {
        with(f) {
            note.setText(notesDto.noteStr)
            deleteNote.isVisible = notesDto.noteStr!!.isNotEmpty()
        }
    }
}