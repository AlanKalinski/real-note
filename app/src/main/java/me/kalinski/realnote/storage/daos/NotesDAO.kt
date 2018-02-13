package me.kalinski.realnote.storage.daos

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.Constants
import me.kalinski.realnote.storage.data.Note
import timber.log.Timber
import javax.inject.Inject


class NotesDAO @Inject constructor(
        val userDAO: UserDAO
) {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(Constants.Database.NOTES_TABLE)

    fun getReferencedNotes(documentReferences: Iterable<DocumentReference>) = Maybe.create<List<Note>> { emitter ->
        Observable.fromIterable(documentReferences)
                .flatMapMaybe { getReferencedNote(it) }
                .toList()
                .subscribeBy(onError = {
                    emitter.onSuccess(emptyList())
                    Timber.w("No notes loaded - $it")
                }, onSuccess = {
                    emitter.onSuccess(it)
                    Timber.d("Notes loaded successful")
                })
    }

    fun getReferencedNote(documentReference: DocumentReference) = Maybe.create<Note> { emitter ->
        RxFirestore.getDocument(documentReference)
                .map({ it.toObject(Note::class.java) })
                .subscribeBy(onError = {
                    emitter.onError(Throwable("Note doesn't exist"))
                }, onSuccess = {
                    Timber.d("Note loaded $it")
                    emitter.onSuccess(it)
                })
    }

/*    fun insertOrUpdate(note: Flowable<Note>) = Single.create<Boolean> { emitter ->
        note
                .flatMapCompletable {
                    RxFirestore.setDocument(collection.document(it.uid), it)
                            .subscribeBy()
                            .andThen { userDAO.linkNote(collection.document()) }
                            .andThen()
                            .subscribeBy(
                                    onError = { emitter.onSuccess(false) },
                                    onSuccess = { emitter.onSuccess(it) }
                            )
                }
    }*/

    fun insertOrUpdate(notes: List<Note>) = Single.create<Boolean> { emitter ->
        Observable.fromIterable(notes)
                .map {
                    if (it.uid == null)
                        it.uid = collection.document().id
                    it
                }
                .flatMapCompletable {
                    RxFirestore.setDocument(collection.document(it.uid
                            ?: collection.document().id), it)
                }
                .andThen(userDAO.linkNotes(notes))
                .subscribeBy(
                        onError = {
                            Timber.w(it)
                            emitter.onError(it)
                        },
                        onSuccess = {
                            emitter.onSuccess(true)
                        }
                )
    }

}