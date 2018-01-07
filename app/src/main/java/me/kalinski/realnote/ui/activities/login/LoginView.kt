package me.kalinski.realnote.ui.activities.login

import android.support.annotation.StringRes
import com.google.firebase.auth.FirebaseUser
import me.kalinski.realnote.di.base.BaseMvp

interface LoginView : BaseMvp.MvpView {
    fun setUser(user: FirebaseUser)
    fun showNotLoggedIn()
    fun btnSignInVisibility(enable: Boolean)
    fun btnSignOutVisibility(enable: Boolean)
    fun showMessage(message: String)
    fun showMessage(@StringRes message: Int)
    fun showProgress()
    fun hideProgress()
    fun navigateToMain()
}