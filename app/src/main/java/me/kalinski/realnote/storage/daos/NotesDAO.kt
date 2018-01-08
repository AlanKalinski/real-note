package me.kalinski.realnote.storage.daos

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.data.Note

const val NOTES_TABLE = "Notes"

class NotesDAO {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(NOTES_TABLE)

    fun getReferencedNotes(documentReferences: Iterable<DocumentReference>) = Maybe.create<List<Note>> { emitter ->
        Observable.fromIterable(documentReferences)
                .flatMapMaybe { getReferencedNote(it) }
                .toList()
                .subscribeBy(onError = {
                    emitter.onSuccess(emptyList())
                }, onSuccess = {
                    emitter.onSuccess(it)
                })
    }

    fun getReferencedNote(documentReference: DocumentReference) = Maybe.create<Note> { emitter ->
        RxFirestore.getDocument(documentReference)
                .map({it.toObject(Note::class.java)})
                .subscribeBy(onError = {
                    emitter.onError(Throwable("Note doesn't exist"))
                }, onSuccess = {
                    emitter.onSuccess(it)
                })
    }

}