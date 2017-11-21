package me.kalinski.realnote.ui.activities.details

import me.kalinski.realnote.storage.data.NotesRepository

interface IDetailsInteractor {
    fun loadNoteDetails(id: String, callback: NotesRepository.GetNoteCallback)
}