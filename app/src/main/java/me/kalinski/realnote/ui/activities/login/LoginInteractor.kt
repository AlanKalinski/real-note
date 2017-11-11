package me.kalinski.realnote.ui.activities.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class LoginInteractor @Inject constructor() : ILoginInteractor {

    private var listener: ILoginPresenter.IntroInteractorListener? = null

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun setListener(listener: ILoginPresenter.IntroInteractorListener) {
        this.listener = listener
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
                        listener?.authSuccessful()
                    } else {
                        listener?.authFailed()
                    }
                }
    }

}