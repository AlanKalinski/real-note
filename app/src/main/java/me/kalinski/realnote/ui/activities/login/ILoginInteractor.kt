package me.kalinski.realnote.ui.activities.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface ILoginInteractor {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount)
    fun setListener(listener: ILoginPresenter.IntroInteractorListener)
}