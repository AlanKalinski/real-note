package me.kalinski.realnote.di.activities

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.utils.Displayable
import me.kalinski.realnote.di.activities.utils.DisplayableProcessor
import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.utils.extensions.inflate
import org.jetbrains.anko.backgroundColor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseActivity(
        _toolbarTitle: Any = "",
        _showToolbar: Boolean = true,
        _showBackarrow: Boolean = true,
        _toolbarSubtitle: Any? = null,
        _toolbarColor: Int = R.color.primary,
        _statusbarColor: Int = R.color.primary_dark
) : BaseToolbar(
        _toolbarTitle,
        _showToolbar,
        _showBackarrow,
        _toolbarSubtitle,
        _toolbarColor,
        _statusbarColor
), HasSupportFragmentInjector, BaseMvp.MvpView {

    @Inject lateinit var injector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var displayableProcessor: DisplayableProcessor

    private val FRAME_LAYOUT_ID by lazy { View.generateViewId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        androidInject()
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        val coordinator = CoordinatorLayout(this).also {
            it.fitsSystemWindows = true
        }

        val contentView = coordinator.inflate(layoutResID).also {
            val params = it.layoutParams as CoordinatorLayout.LayoutParams
            params.behavior = AppBarLayout.ScrollingViewBehavior()
            it.layoutParams = params
        }

        if (_showToolbar) coordinator.addView(coordinator.inflate(R.layout.appbar_layout))
        coordinator.addView(contentView)

        val decorView = this.window.decorView as ViewGroup
        decorView.addView(attachInfoView(this), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        super.setContentView(coordinator)
    }

    private fun attachInfoView(context: Context): ViewGroup = FrameLayout(context).also {
        it.id = FRAME_LAYOUT_ID
        it.tag = "displayable.tag"
        it.backgroundColor = ContextCompat.getColor(context, R.color.splash_background)
        it.isClickable = true
        it.visibility = View.GONE
        it.fitsSystemWindows = true
    }

    override fun onResume() {
        displayableProcessor.attachView({ showSwocaseAction(it) }, { hideShowcaseAction(it) })
        super.onResume()
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

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> true
    }

    private fun showSwocaseAction(it: Displayable) {
        val view = findViewById<View>(FRAME_LAYOUT_ID)

        if (view != null) {
            view.visibility = View.VISIBLE
            val fragment = it.getDisplayItem()
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up_new, R.anim.slide_in_down_new, R.anim.slide_in_up_new, R.anim.slide_in_down_new)
                    .replace(FRAME_LAYOUT_ID, fragment, it.getTagName())
                    .addToBackStack(it.getTagName())
                    .commit()
        }
    }

    private fun hideShowcaseAction(it: Displayable) {
        hideKeyboard()
        supportFragmentManager.popBackStack(it.getTagName(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (displayableProcessor.hasMoreInQueue()) {
            startTimerAction(150, { displayableProcessor.show() })
        } else {
            supportFragmentManager.addOnBackStackChangedListener(object : FragmentManager.OnBackStackChangedListener {
                override fun onBackStackChanged() {
                    supportFragmentManager.removeOnBackStackChangedListener(this)
                    startTimerAction(400, {
                        val view = findViewById<View>(FRAME_LAYOUT_ID)
                        if (view != null) view.visibility = View.GONE
                    })
                }
            })
        }
    }

    fun startTimerAction(
            timeInMillis: Long,
            action: () -> Unit,
            error: (Throwable) -> Unit = { it.printStackTrace() }
    ) = Observable.timer(timeInMillis, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(error, action)


    override fun onBackPressed() {
        if (displayableProcessor.hasMoreInQueue())
            displayableProcessor.hide()
        else
            super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        displayableProcessor.detachView()
    }

    protected open fun androidInject() {
        AndroidInjection.inject(this)
    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector
}