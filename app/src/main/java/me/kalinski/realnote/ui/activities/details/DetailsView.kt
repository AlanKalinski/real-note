package me.kalinski.realnote.ui.activities.details

import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.realnote.storage.models.Note

interface DetailsView : BaseMvp.MvpView {
    fun showNoteDetails(note: Note)
}