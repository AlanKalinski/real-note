package me.kalinski.realnote.ui.activities.intro

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface IIntroInteractor {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount)
}