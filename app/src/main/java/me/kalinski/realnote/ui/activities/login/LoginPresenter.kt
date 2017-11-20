package me.kalinski.realnote.ui.activities.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class LoginPresenter @Inject constructor(val interactor: ILoginInteractor) : ILoginPresenter {

    var view: LoginView? = null

    val interactorListener = object : ILoginPresenter.IntroInteractorListener {
        override fun authFailed() {
            showNotLogged()
        }

        override fun authSuccessful() {
            checkUser()
        }
    }

    init {
        interactor.setListener(interactorListener)
    }

    override fun attachView(view: LoginView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        interactor.firebaseAuthWithGoogle(account)
    }

    override fun signOut() {
        interactor.signOut()
//        view.googleApiSignOut()
    }

    override fun revokeAccess() {
        interactor.signOut()
//        view.revokeAccess()
    }

    override fun checkUser() {
        interactor.getCurrentUser()?.let {
            view?.setUser(it)
            view?.btnSignInVisibility(false)
            view?.btnSignOutVisibility(true)
        } ?: run {
            showNotLogged()
        }
    }

    override fun showNotLogged() {
        view?.showNotLoggedIn()
        view?.btnSignInVisibility(true)
        view?.btnSignOutVisibility(false)
    }
}