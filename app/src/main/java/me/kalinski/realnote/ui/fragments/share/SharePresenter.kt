package me.kalinski.realnote.ui.fragments.share

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.ui.fragments.share.adapter.ShareUserWrapper
import timber.log.Timber
import javax.inject.Inject

class SharePresenter @Inject constructor(val interactor: IShareInteractor) : ISharePresenter {

    var view: IShareView? = null

    override fun attachView(view: IShareView) {
        this.view = view
    }

    override fun loadUsers() {
        interactor.loadWrappedUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    Timber.w(it)
                    view?.showErrorLoading()
                }, onSuccess = {
                    view?.showUsers(it)
                })
    }

    override fun shareNote(noteToShare: Note, usersToShare: List<ShareUserWrapper>) {
        interactor.shareNote(noteToShare, usersToShare)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    view?.showErrorSharing()
                }, onComplete = {
                    view?.navigateBack()
                })
    }

    override fun detachView() {
        view = null
    }
}