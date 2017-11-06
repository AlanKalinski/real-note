package me.kalinski.realnote.ui.activities.intro

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class IntroInteractor constructor(val listener: IIntroPresenter.IntroInteractorListener) : IIntroInteractor {
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser() = auth.currentUser

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.authSuccessful()
                    } else {
                        listener.authFailed()
                    }
                }
    }

}