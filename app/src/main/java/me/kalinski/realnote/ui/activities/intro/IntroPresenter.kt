package me.kalinski.realnote.ui.activities.intro

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class IntroPresenter constructor(val view: IntroView) : IIntroPresenter {

    val interactor: IIntroInteractor by lazy {
        IntroInteractor(interactorListener)
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
            view.setUserName(it.displayName)
            view.btnSignInVisibility(false)
            view.btnSignOutVisibility(true)
        } ?: run {
            showNotLogged()
        }
    }

    override fun showNotLogged() {
        view.showNotLoggedIn()
        view.btnSignInVisibility(true)
        view.btnSignOutVisibility(false)
    }

    val interactorListener = object : IIntroPresenter.IntroInteractorListener {
        override fun authFailed() {
            showNotLogged()
        }

        override fun authSuccessful() {
            checkUser()
        }
    }
}