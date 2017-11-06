package me.kalinski.realnote.ui.activities.intro

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface IIntroPresenter {
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