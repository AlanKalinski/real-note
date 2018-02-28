package me.kalinski.realnote.ui.fragments.share

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import me.kalinski.realnote.storage.daos.UserDAO
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.storage.models.User
import me.kalinski.realnote.ui.fragments.share.adapter.ShareUserWrapper
import javax.inject.Inject

class ShareInteractor @Inject constructor(
        val userDAO: UserDAO
) : IShareInteractor {
    override fun shareNote(noteToShare: Note, usersToShare: List<ShareUserWrapper>) =
            Observable.fromIterable(usersToShare)
                    .flatMapSingle { userDAO.linkNotes(Flowable.just(noteToShare), User(displayName = it.displayName, email = it.email)) }


    override fun loadWrappedUsers() = Single.create<List<ShareUserWrapper>> { emitter ->
        userDAO.getAllUsers()
                .subscribeBy(onError = {
                    emitter.onError(it)
                }, onSuccess = {
                    Observable.fromIterable(it)
                            .filter { it.email != userDAO.actualUser?.email }
                            .map { ShareUserWrapper(it.displayName, it.email) }
                            .toList()
                            .subscribeBy(onError = {
                                emitter.onError(it)
                            }, onSuccess = {
                                emitter.onSuccess(it)
                            })
                })
    }
}