package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.realnote.storage.models.Note

interface AddNoteView : BaseMvp.MvpView {
    fun onNoteSaved(note: Note)
}