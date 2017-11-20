package me.kalinski.realnote.ui.activities.main

import javax.inject.Inject

class MainPresenter @Inject constructor(interactor: IMainInteractor) : IMainPresenter {

    var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}