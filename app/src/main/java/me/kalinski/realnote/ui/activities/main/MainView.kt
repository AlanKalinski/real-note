package me.kalinski.realnote.ui.activities.main

import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.realnote.storage.data.Note

interface MainView : BaseMvp.MvpView {
    fun showNotes(notes: List<Note>)
}