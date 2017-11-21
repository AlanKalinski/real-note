package me.kalinski.realnote.ui.activities.main

import me.kalinski.realnote.storage.data.NotesRepository

interface IMainInteractor {
    fun loadNotes(callback: NotesRepository.LoadNotesCallback)
    fun refreshData()
}