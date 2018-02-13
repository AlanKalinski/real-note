package me.kalinski.realnote.ui.activities.main

import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.daos.NotesDAO
import me.kalinski.realnote.storage.daos.UserDAO
import me.kalinski.realnote.storage.models.Note
import timber.log.Timber
import javax.inject.Inject

class MainInteractor @Inject constructor(
        val notesDAO: NotesDAO,
        val userDAO: UserDAO
) : IMainInteractor {
    override fun loadNotes(): Single<List<Note>> = Single.create { emitter ->
        userDAO.actualUser?.notes?.let {
            notesDAO.getReferencedNotes(it)
                    .subscribeBy(onError = {
                        Timber.w(it)
                        emitter.onError(it)
                    }, onSuccess = {
                        Timber.d("Notes loaded: $it")
                        emitter.onSuccess(it)
                    })
        } ?: emitter.onSuccess(emptyList())
    }
}