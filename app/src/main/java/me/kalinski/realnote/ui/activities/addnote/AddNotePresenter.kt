package me.kalinski.realnote.ui.activities.addnote

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.kalinski.realnote.storage.models.Note
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AddNotePresenter @Inject constructor(val interactor: IAddNoteInteractor) : IAddNotePresenter {
    override fun saveNote(noteTitle: String, noteHtml: String) {
        val noteToSave = Note(noteTitle, noteHtml, Date().time)
        Timber.d("Note: %s", noteToSave.toString())
        interactor.saveNote(noteToSave)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    TODO("Not saved action not implemented")
                }, onSuccess = {
                    if (it) view?.onNoteSaved()
                    else TODO("Not saved action not implemented")
                })
    }

    var view: AddNoteView? = null

    override fun attachView(view: AddNoteView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}