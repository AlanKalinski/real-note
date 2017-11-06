package me.kalinski.firebaseauth.listeners

import com.google.android.gms.common.ConnectionResult
import com.google.firebase.auth.FirebaseUser

interface AuthorizationListener {
    fun loggedOut()
    fun signedIn(it: FirebaseUser)
    fun onError(error: ConnectionResult)
}