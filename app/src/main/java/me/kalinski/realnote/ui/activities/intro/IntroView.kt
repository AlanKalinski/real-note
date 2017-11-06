package me.kalinski.realnote.ui.activities.intro

interface IntroView {
    fun setUserName(displayName: String?)
    fun showNotLoggedIn()
    fun performSignIn()
    fun disconnect()
    fun signOut()
    fun googleApiSignOut()
    fun btnSignInVisibility(enable: Boolean)
    fun btnSignOutVisibility(enable: Boolean)
    fun revokeAccess()
    fun showProgress()
    fun hideProgress()
}