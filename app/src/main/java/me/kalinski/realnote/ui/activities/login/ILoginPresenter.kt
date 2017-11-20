package me.kalinski.realnote.ui.activities.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import me.kalinski.realnote.di.base.BaseMvp

interface ILoginPresenter : BaseMvp.MvpPresenter<LoginView> {
    interface IntroInteractorListener {
        fun authSuccessful()
        fun authFailed()
    }

    fun checkUser()
    fun signOut()
    fun revokeAccess()
    fun showNotLogged()
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount)
}