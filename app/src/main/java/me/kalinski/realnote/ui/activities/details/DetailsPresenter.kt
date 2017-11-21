package me.kalinski.realnote.ui.activities.details

import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.storage.data.NotesRepository
import javax.inject.Inject

class DetailsPresenter @Inject constructor(val interactor: IDetailsInteractor) : IDetailsPresenter {

    var view: DetailsView? = null

    override fun attachView(view: DetailsView) {
        this.view = view
    }

    override fun loadNoteDetails(id: String) {
        interactor.loadNoteDetails(id, notesRepositoryCallback())
    }

    private fun notesRepositoryCallback() = object : NotesRepository.GetNoteCallback {
        override fun onNoteLoaded(note: Note) {
            view?.showNoteDetails(note)
        }
    }

    override fun detachView() {
        view = null
    }
}