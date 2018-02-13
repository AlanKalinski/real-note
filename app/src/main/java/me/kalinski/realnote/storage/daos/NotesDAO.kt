package me.kalinski.realnote.storage.daos

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.Constants
import me.kalinski.realnote.storage.models.Note
import timber.log.Timber
import javax.inject.Inject


class NotesDAO @Inject constructor(
        val userDAO: UserDAO
) {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(Constants.Database.NOTES_TABLE)

    fun getReferencedNotes(documentReferences: Iterable<DocumentReference>) = Single.create<List<Note>> { emitter ->
        Observable.fromIterable(documentReferences)
                .flatMapMaybe { RxFirestore.getDocument(it) }
                .map({ it.toObject(Note::class.java) })
                .toList()
                .subscribeBy(onError = {
                    Timber.w(it)
                    emitter.onError(Throwable("No notes loaded - $it"))
                }, onSuccess = {
                    Timber.d("Notes loaded successful")
                    emitter.onSuccess(it)
                })
    }

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