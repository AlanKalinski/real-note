package me.kalinski.realnote.ui.activities.details

import javax.inject.Inject

class DetailsPresenter @Inject constructor(interactor: IDetailsInteractor) : IDetailsPresenter {

    var view: DetailsView? = null

    override fun attachView(view: DetailsView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}