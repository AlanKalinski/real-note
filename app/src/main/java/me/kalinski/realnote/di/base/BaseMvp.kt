package me.kalinski.realnote.di.base

interface BaseMvp {

    interface MvpView {
    }

    interface MvpPresenter<in V : MvpView> {
        fun attachView(view: V)
        fun detachView()
    }
}
