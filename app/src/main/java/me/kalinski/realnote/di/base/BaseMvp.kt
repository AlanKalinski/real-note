package me.kalinski.realnote.di.base

interface BaseMvp {

    interface MvpView {
        fun showMessage(message: String)
    }

    interface MvpPresenter<in V : MvpView> {
        fun attachView(view: V)
        fun detachView()
    }
}
