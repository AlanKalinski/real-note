package me.kalinski.realnote.di.activities

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
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

    fun setupToolbar(toolbarId: Int, visible: Boolean = false, title: Any? = null, backArrowEnabled: Boolean = false, resId: Int? = null): Toolbar? {
        val myToolbar = findViewById<Toolbar>(toolbarId)
        myToolbar.let {
            if (supportActionBar == null) {
                setSupportActionBar(myToolbar)
            }

            supportActionBar?.apply {
                setDisplayShowHomeEnabled(backArrowEnabled)
                setDisplayHomeAsUpEnabled(backArrowEnabled)

                if (resId != null) this.setIcon(resId)

                when (title) {
                    is Int -> supportActionBar?.title = resources.getText(title)
                    is String -> supportActionBar?.title = title
                }

                if (visible) show() else hide()
            }
        }

        return myToolbar
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> true
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector
}