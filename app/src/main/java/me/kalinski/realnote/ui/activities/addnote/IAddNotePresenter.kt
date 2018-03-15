package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.realnote.storage.models.Note

interface IAddNotePresenter : BaseMvp.MvpPresenter<AddNoteView> {
    fun saveNote(noteToSave: Note)
}