package me.kalinski.realnote.ui.activities.intro

interface IntroView {
    fun setUserName(displayName: String?)
    fun showNotLoggedIn()
    fun btnSignInVisibility(enable: Boolean)
    fun btnSignOutVisibility(enable: Boolean)
    fun showProgress()
    fun hideProgress()
}