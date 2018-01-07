package me.kalinski.realnote.storage.daos

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.storage.models.User
import timber.log.Timber

const val USERS_TABLE = "Users"

class UserDAO {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(USERS_TABLE)

    fun insertOrUpdateUser(user: FirebaseUser) = Single.create<User> { emitter ->
        RxFirestore.getDocument(collection.document(user.email ?: ""))
                .map({
                    val mapObj = it.toObject(User::class.java)
                    mapObj.notes = getReferencedNotes(it.get("Notes") as? List<DocumentReference> ?: emptyList()).blockingGet()
                    mapObj
                })
                .subscribeBy(onError = {
                    val newUser = User(
                            user.displayName ?: "",
                            user.email ?: "",
                            user.phoneNumber ?: "",
                            user.photoUrl.toString(),
                            user.providerId
                    )

                    RxFirestore.setDocument(collection.document(newUser.email), newUser)
                            .subscribeBy(
                                    onError = {
                                        emitter.onError(it)
                                    },
                                    onComplete = {
                                        emitter.onSuccess(newUser)
                                    }
                            )
                }, onSuccess = {
                    it.displayName = user.displayName ?: ""
                    it.phoneNumber = user.phoneNumber ?: ""
                    it.photoUrl = user.photoUrl.toString()

                    Timber.d("User ${it.toString()} ")
                    Timber.d("User note: ${it.notes[0].toString()}")

                    RxFirestore.setDocument(collection.document(it.email), it)
                            .subscribeBy(
                                    onError = {
                                        emitter.onError(it)
                                    },
                                    onComplete = {
                                        emitter.onSuccess(it)
                                    }
                            )
                })
    }

    private fun getReferencedNotes(documentReferences: Iterable<DocumentReference>) = Maybe.create<Array<Note>> { emitter ->
        Observable.fromIterable(documentReferences)
                .map {
                    RxFirestore.getDocument(it)
                            .map({ it.toObject(Note::class.java) })
                            .doOnError({ it.printStackTrace() })
                            .blockingGet()
                }
                .toList()
                .subscribeBy(onError = {
                    emitter.onSuccess(emptyArray())
                }, onSuccess = {
                    emitter.onSuccess(it.toTypedArray())
                })
    }

}