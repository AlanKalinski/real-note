package me.kalinski.realnote.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_intro.*
import me.kalinski.firebaseauth.components.Authentication
import me.kalinski.firebaseauth.components.GoogleAuthentication
import me.kalinski.firebaseauth.listeners.AuthorizationListener
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    private val progress by lazy { indeterminateProgressDialog(getString(R.string.wait)) }

    @Inject lateinit var presenter: ILoginPresenter

    private var authorizationModule: Authentication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        authorizationModule = GoogleAuthentication(this, authorizationListener(), getString(R.string.default_web_client_id))

        btnSignIn.setOnClickListener {
            showProgress()
            authorizationModule?.signIn()
        }
        btnSignOut.setOnClickListener {
            showProgress()
            authorizationModule?.signOut()
        }
        btnDisconnect.setOnClickListener {
            showProgress()
            authorizationModule?.signOut()
        }
    }

    private fun authorizationListener(): AuthorizationListener = object : AuthorizationListener {
        override fun onError(error: ConnectionResult) {
            showError()
        }

        override fun loggedOut() {
            showNotLoggedIn()
        }

        override fun signedIn(it: FirebaseUser) {
            setUserName(it.displayName)
        }
    }

    private fun showError() {
        hideProgress()
        toast(getString(R.string.error_log_in))
    }

    override fun onStart() {
        super.onStart()
        presenter.checkUser()
    }

    override fun setUserName(displayName: String?) {
        hideProgress()
        status.text = String.format(getString(R.string.logged_as), displayName)
        btnSignInVisibility(false)
        btnSignOutVisibility(true)
    }

    override fun showNotLoggedIn() {
        hideProgress()
        status.text = String.format(getString(R.string.not_logged))
        btnSignInVisibility(true)
        btnSignOutVisibility(false)
    }

    override fun showProgress() {
        progress.show()
    }

    override fun hideProgress() {
        progress.hide()
    }

    override fun btnSignInVisibility(enable: Boolean) {
        btnSignIn.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun btnSignOutVisibility(enable: Boolean) {
        signOutAndDisconnect.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let { authorizationModule?.recognizeActivityResult(requestCode, resultCode, data) }
    }
}
