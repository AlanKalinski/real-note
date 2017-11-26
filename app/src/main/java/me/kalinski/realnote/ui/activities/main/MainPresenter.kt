package me.kalinski.realnote.ui.activities.main

import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.storage.data.NotesRepository
import javax.inject.Inject

class MainPresenter @Inject constructor(val interactor: IMainInteractor) : IMainPresenter {
    var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun loadNotes() {
        view?.showProgress()
        interactor.loadNotes(notesDbCallback())
    }

    private fun notesDbCallback() = object : NotesRepository.LoadNotesCallback {
        override fun onNotesLoaded(notes: List<Note>) {
            view?.showNotes(notes)
            view?.hideProgress()
        }
    }


    override fun detachView() {
        view = null
    }
}