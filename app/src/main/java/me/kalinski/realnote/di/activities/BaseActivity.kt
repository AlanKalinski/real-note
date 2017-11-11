package me.kalinski.realnote.di.activities

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import me.kalinski.realnote.di.base.BaseMvp
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, BaseMvp.MvpView {

    @Inject lateinit var injector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        androidInject()
        super.onCreate(savedInstanceState)
    }

    fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name == it.service.className }
    }

    fun showKeyboard() {
        val view = window.decorView.findViewById<View>(android.R.id.content)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)

    }

    open fun hideKeyboard() {
        val view = window.decorView.findViewById<View>(android.R.id.content)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected open fun androidInject() {
        AndroidInjection.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return injector
    }
}