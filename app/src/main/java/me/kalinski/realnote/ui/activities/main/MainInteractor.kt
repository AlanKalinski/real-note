package me.kalinski.realnote.ui.activities.main

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.kalinski.realnote.storage.daos.NotesDAO
import me.kalinski.realnote.storage.daos.UserDAO
import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.storage.data.NotesRepository
import me.kalinski.realnote.storage.data.NotesServiceApiEndpoint
import timber.log.Timber
import javax.inject.Inject

class MainInteractor @Inject constructor(
        val notesRepository: NotesRepository,
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

    override fun saveSampleNotes(): Completable = Completable.create { emitter ->
        Observable.fromIterable(NotesServiceApiEndpoint.loadPersistedNotes().toList())
                .subscribeOn(Schedulers.io())
                .toList()
                .flatMap { notesDAO.insertOrUpdate(it) }
                .subscribeBy(onError = {
                    Timber.w(it)
                    emitter.onError(it)
                }, onSuccess = {
                    Timber.d("Notes saved? : $it")
                    emitter.onComplete()
                })
    }
}