package me.kalinski.realnote.ui.activities.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainPresenter @Inject constructor(val interactor: IMainInteractor) : IMainPresenter {
    var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun loadNotes() {
        interactor.saveSampleNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    Timber.w(it)
                }, onComplete = {
                    view?.showProgress()
                    interactor.loadNotes()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(onError = {
                                //TODO
                                view?.hideProgress()
                            }, onSuccess = {
                                view?.showNotes(it)
                                view?.hideProgress()
                            })
                })
    }

    override fun detachView() {
        view = null
    }
}