package me.kalinski.realnote.ui.activities.addnote

import javax.inject.Inject

class AddNotePresenter @Inject constructor(interactor: IAddNoteInteractor) : IAddNotePresenter {

    var view: AddNoteView? = null

    override fun attachView(view: AddNoteView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}