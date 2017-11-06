package me.kalinski.realnote.ui.activities.intro

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_intro.*
import me.kalinski.realnote.R
import org.jetbrains.anko.indeterminateProgressDialog
import timber.log.Timber

class IntroActivity : AppCompatActivity(), IntroView {
    private val RC_SIGN_IN = 9001
    private val progress by lazy { indeterminateProgressDialog("Czekaj...") }

    private val presenter: IIntroPresenter by lazy {
        IntroPresenter(this)
    }

    private val googleApiClient by lazy {
        GoogleApiClient.Builder(this)
                .enableAutoManage(this) { error ->
                    run {
                        //TODO Error handling
                        Timber.w(error.toString())
                    }
                }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btnSignIn.setOnClickListener { performSignIn() }
        btnSignOut.setOnClickListener { signOut() }
        btnDisconnect.setOnClickListener { disconnect() }
    }

    override fun onStart() {
        super.onStart()
        googleApiClient
        presenter.checkUser()
    }

    override fun setUserName(displayName: String?) {
        hideProgress()
        status.text = String.format("Zalogowano jako: %s", displayName)
    }

    override fun showNotLoggedIn() {
        hideProgress()
        status.text = String.format("Użytkownik niezalogowany")
    }

    override fun performSignIn() {
        showProgress()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun showProgress() {
        progress.show()
    }

    override fun hideProgress() {
        progress.hide()
    }

    override fun signOut() {
        showProgress()
        presenter.signOut()
    }

    override fun disconnect() {
        showProgress()
        presenter.revokeAccess()
    }

    override fun revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback { presenter.showNotLogged() }
    }

    override fun googleApiSignOut() {
        if (googleApiClient.isConnected) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { presenter.showNotLogged() }
        }
    }

    override fun btnSignInVisibility(enable: Boolean) {
        btnSignIn.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun btnSignOutVisibility(enable: Boolean) {
        signOutAndDisconnect.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                account?.let { presenter.firebaseAuthWithGoogle(account) } ?: run { presenter.showNotLogged() }
            } else {
                presenter.showNotLogged()
            }
        }
    }
}
