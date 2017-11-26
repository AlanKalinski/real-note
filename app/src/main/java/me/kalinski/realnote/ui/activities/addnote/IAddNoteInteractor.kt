package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.storage.data.NotesRepository

interface IAddNoteInteractor {
    fun saveNote(note: Note, callback: NotesRepository.SaveNoteCallback)
}