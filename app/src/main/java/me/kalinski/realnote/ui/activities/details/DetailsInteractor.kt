package me.kalinski.realnote.ui.activities.details

import me.kalinski.realnote.storage.data.NotesRepository
import javax.inject.Inject

class DetailsInteractor @Inject constructor(val notesRepository: NotesRepository) : IDetailsInteractor {
    override fun loadNoteDetails(id: String, callback: NotesRepository.GetNoteCallback) {
        notesRepository.getNote(id, callback)
    }
}