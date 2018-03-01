package me.kalinski.realnote.ui.fragments.login

import me.kalinski.realnote.di.base.BaseMvp

interface ILoginView : BaseMvp.MvpView {
    fun showLoginView()
    fun showRegisterView()

}