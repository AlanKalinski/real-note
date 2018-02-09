package me.kalinski.realnote.storage.daos

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.data.Note
import timber.log.Timber
import javax.inject.Inject

const val NOTES_TABLE = "Notes"

class NotesDAO @Inject constructor() {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(NOTES_TABLE)

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
                .map({it.toObject(Note::class.java)})
                .subscribeBy(onError = {
                    emitter.onError(Throwable("Note doesn't exist"))
                }, onSuccess = {
                    Timber.d("Note loaded $it")
                    emitter.onSuccess(it)
                })
    }

}