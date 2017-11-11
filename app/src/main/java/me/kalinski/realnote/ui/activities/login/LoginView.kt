package me.kalinski.realnote.ui.activities.login

import me.kalinski.realnote.di.base.BaseMvp

interface LoginView : BaseMvp.MvpView {
    fun setUserName(displayName: String?)
    fun showNotLoggedIn()
    fun btnSignInVisibility(enable: Boolean)
    fun btnSignOutVisibility(enable: Boolean)
    fun showProgress()
    fun hideProgress()
}