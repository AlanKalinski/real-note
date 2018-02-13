package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.storage.models.Note
import javax.inject.Inject

class AddNoteInteractor @Inject constructor() : IAddNoteInteractor {
    override fun saveNote(note: Note) {
//        repository.saveNote(note, callback)
    }
}