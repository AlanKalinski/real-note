package me.kalinski.realnote.ui.fragments.login

import com.google.firebase.auth.FirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

class LoginPresenter @Inject constructor() : ILoginPresenter {

    var mode: Modes by Delegates.observable(Modes.LOGIN, onChange = { _, oldValue, newValue ->
        if (oldValue != newValue) reloadViewDependOnMode()
    })

    var view: ILoginView? = null

    override fun attachView(view: ILoginView) {
        this.view = view
    }

    override fun changeMode() {
        this.mode = if (mode == Modes.LOGIN) Modes.REGISTER else Modes.LOGIN
    }

    override fun reloadViewDependOnMode() {
        when (mode) {
            Modes.LOGIN -> {
                view?.showLoginView()
            }
            Modes.REGISTER -> {
                view?.showRegisterView()
            }
        }
    }

    override fun doAction(login: String, password: String, repeat: String) {
        when (mode) {
            Modes.LOGIN -> {
                RxFirebaseAuth.signInWithEmailAndPassword(FirebaseAuth.getInstance(), login, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(onError = {
                            it.printStackTrace()
                        }, onSuccess = {
                            Timber.d("Login success : ${it.toString()}")
                        }, onComplete = {
                            Timber.d("Login completed")
                        })
            }
            Modes.REGISTER -> {
                if (password == repeat) {
                    RxFirebaseAuth.createUserWithEmailAndPassword(FirebaseAuth.getInstance(), login, password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(onError = {
                                it.printStackTrace()
                            }, onSuccess = {
                                Timber.d("Register success : ${it.toString()}")
                            }, onComplete = {
                                Timber.d("Register completed")
                            })
                }
            }
        }
    }

    override fun detachView() {
        this.view = null
    }
}

enum class Modes {
    LOGIN,
    REGISTER
}