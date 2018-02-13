package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.storage.models.Note

interface IAddNoteInteractor {
    fun saveNote(note: Note)
}