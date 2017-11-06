package me.kalinski.firebaseauth.components

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import me.kalinski.firebaseauth.listeners.AuthorizationListener

abstract class Authentication(
        protected val activity: FragmentActivity,
        protected val listener: AuthorizationListener,
        protected val requestToken: String
) {
    private val TAG = javaClass.simpleName
    protected val RC_SIGN_IN = 9001

    protected val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(requestToken)
                .requestEmail()
                .build()
    }

    protected val googleApiClient by lazy {
        GoogleApiClient.Builder(activity)
                .enableAutoManage(activity) { error ->
                    run {
                        Log.w(TAG, error.toString())
                        listener.onError(error)
                    }
                }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    protected fun checkUser() {
        auth.currentUser?.let {
            listener.signedIn(it)
        } ?: run {
            listener.loggedOut()
        }
    }

    abstract fun signIn()
    abstract fun signOut()
    abstract fun recognizeActivityResult(requestCode: Int, resultCode: Int, data: Intent)
}