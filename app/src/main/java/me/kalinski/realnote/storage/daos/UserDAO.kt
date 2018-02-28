package me.kalinski.realnote.storage.daos

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.kalinski.realnote.storage.Constants
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.storage.models.User
import timber.log.Timber
import javax.inject.Inject

class UserDAO @Inject constructor() {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(Constants.Database.USERS_TABLE)

    var actualUser: User? = null

    fun insertOrUpdate(user: FirebaseUser) = Single.create<User> { emitter ->
        RxFirestore.getDocument(collection.document(user.email ?: ""))
                .map({ it.toObject(User::class.java) })
                .toSingle()
                .subscribeBy(onError = {
                    Timber.w(it)
                    val newUser = User(
                            user.displayName ?: "",
                            user.email ?: "",
                            user.phoneNumber ?: "",
                            user.photoUrl.toString(),
                            user.providerId
                    )

                    actualUser = newUser
                    update(newUser).subscribeBy(onError = {
                        Timber.w(it)
                        emitter.onError(it)
                    }, onComplete = {
                        emitter.onSuccess(newUser)
                    })
                }, onSuccess = {
                    it.displayName = user.displayName ?: ""
                    it.phoneNumber = user.phoneNumber ?: ""
                    it.photoUrl = user.photoUrl.toString()

                    Timber.d("User $it ")

                    actualUser = it
                    update(it).subscribeBy(onError = {
                        Timber.w(it)
                        emitter.onError(it)
                    }, onComplete = {
                        emitter.onSuccess(it)
                    })
                })
    }

    private fun update(user: User) = RxFirestore.setDocument(collection.document(user.email), user)

    fun linkNotes(notes: Flowable<Note>, user: User? = actualUser) = Single.create<Boolean> { emitter ->
        notes
                .map { reference.collection(Constants.Database.NOTES_TABLE).document(it.uid!!) }
                .toList()
                .subscribeBy(onError = {
                    Timber.w(it)
                    emitter.onError(it)
                }, onSuccess = {
                    val notesRef = it
                    user?.let {
                        it.notes.addAll(notesRef)
                        update(it)
                                .subscribeOn(Schedulers.io())
                                .subscribeBy(onError = {
                                    Timber.w(it)
                                    emitter.onSuccess(false)
                                }, onComplete = {
                                    Timber.d("User updated")
                                    emitter.onSuccess(true)
                                })
                    } ?: kotlin.run {
                        emitter.onError(Throwable("No user logged in!"))
                    }
                })
    }

    fun getAllUsers(): Single<List<User>> = Single.create { emitter ->
        RxFirestore.getCollection(collection)
                .map { it.toObjects(User::class.java) }
                .subscribeBy(onError = {
                    emitter.onError(it)
                }, onSuccess = {
                    emitter.onSuccess(it)
                }
                )
    }
}