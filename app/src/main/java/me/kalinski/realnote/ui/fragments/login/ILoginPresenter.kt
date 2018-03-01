package me.kalinski.realnote.ui.fragments.login

import me.kalinski.realnote.di.base.BaseMvp

interface ILoginPresenter : BaseMvp.MvpPresenter<ILoginView> {

    fun reloadViewDependOnMode()
    fun changeMode()
    fun doAction(login: String, password: String, repeat: String)
}