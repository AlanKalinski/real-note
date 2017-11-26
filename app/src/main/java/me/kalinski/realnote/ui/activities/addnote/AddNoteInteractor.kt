package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.storage.data.NotesRepository
import javax.inject.Inject

class AddNoteInteractor @Inject constructor(val repository: NotesRepository) : IAddNoteInteractor {
    override fun saveNote(note: Note, callback: NotesRepository.SaveNoteCallback) {
        repository.saveNote(note, callback)
    }
}