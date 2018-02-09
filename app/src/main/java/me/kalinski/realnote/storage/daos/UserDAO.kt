package me.kalinski.realnote.storage.daos

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.models.User
import timber.log.Timber
import javax.inject.Inject

const val USERS_TABLE = "Users"

class UserDAO @Inject constructor() {
    private val reference = FirebaseFirestore.getInstance()
    private val collection = reference.collection(USERS_TABLE)

    var actualUser: User? = null

    fun insertOrUpdateUser(user: FirebaseUser) = Single.create<User> { emitter ->
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

                    Timber.d("User $it ")

                    actualUser = it
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
}