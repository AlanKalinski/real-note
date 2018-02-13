package me.kalinski.realnote.ui.activities.addnote

import me.kalinski.realnote.storage.models.Note
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AddNotePresenter @Inject constructor(val interactor: IAddNoteInteractor) : IAddNotePresenter {
    override fun saveNote(noteTitle: String, noteHtml: String) {
        val noteToSave = Note(noteTitle, noteHtml, Date().time)
        Timber.d("Note: %s", noteToSave.toString())
        interactor.saveNote(noteToSave)
    }

//    private fun onNoteSaveCallback() = object : NotesRepository.SaveNoteCallback {
//        override fun onNoteSaved(note: Note) {
//            Timber.d("Note saved: %s", note.toString())
//            view?.onNoteSaved(note)
//        }
//
//    }

    var view: AddNoteView? = null

    override fun attachView(view: AddNoteView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}