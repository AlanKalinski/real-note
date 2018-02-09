package me.kalinski.firebaseauth.components

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import me.kalinski.firebaseauth.listeners.AuthorizationListener

class GoogleAuthentication(activity: FragmentActivity, listener: AuthorizationListener, requestToken: String) : Authentication(activity, listener, requestToken) {
    override fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signOut() {
        auth.signOut()
        if (googleApiClient.isConnected) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { listener.loggedOut() }
        }
    }

    override fun recognizeActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                account?.let { firebaseAuthWithGoogle(account) } ?: run { listener.loggedOut() }
            } else {
                Log.w(javaClass.simpleName, "${result.status.statusMessage}")
                listener.loggedOut()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        checkUser()
                    } else {
                        listener.loggedOut()
                    }
                }
    }
}