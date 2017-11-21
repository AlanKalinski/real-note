package me.kalinski.realnote.ui.activities.main

import me.kalinski.realnote.storage.data.NotesRepository
import javax.inject.Inject

class MainInteractor @Inject constructor(val notesRepository: NotesRepository) : IMainInteractor {
    override fun loadNotes(callback: NotesRepository.LoadNotesCallback) {
        notesRepository.getNotes(callback)
    }

    override fun refreshData(){
        notesRepository.refreshData()
    }
}