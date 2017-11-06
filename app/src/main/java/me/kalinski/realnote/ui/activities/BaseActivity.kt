package me.kalinski.realnote.ui.activities

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
/*
    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }
    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return frameworkFragmentInjector
    }

    fun setupToolbar(toolbarId: Int, visible: Boolean = false, title: Any? = null, backArrowEnabled: Boolean = false, resId: Int? = null) {
        val myToolbar = findViewById<Toolbar>(toolbarId)
        if(supportActionBar == null) {
            setSupportActionBar(myToolbar)
        }

        if(myToolbar != null) {

            supportActionBar?.apply {

                setDisplayShowHomeEnabled(backArrowEnabled)
                setDisplayHomeAsUpEnabled(backArrowEnabled)

                if(resId != null) this.setIcon(resId)

                when(title) {
                    is Int -> supportActionBar?.title = resources.getText(title)
                    is String -> supportActionBar?.title = title
                }

                if(visible) show() else hide()
            }
        }
    }

    fun configureSearchViewCursor(searchView: SearchView) {
        try {
            val searchTextView = searchView.findViewById<AppCompatAutoCompleteTextView>(android.support.v7.appcompat.R.id.search_src_text) as AutoCompleteTextView

            val mCursorDrawableRes = TextView::class.java.getDeclaredField ("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            mCursorDrawableRes.set(searchTextView, 0)
            print("test")
        } catch (ex: Exception) {
            print("test")
        }
    }

    fun checkAction(title: String = "", function: () -> Unit, errorFunction: () -> Unit) {
        val checkDialog = CheckFragment.getFragment(title)
        checkDialog.listener = object : CheckDialogListener {
            override fun onSuccess() {
                function.invoke()
            }

            override fun onError(error: Throwable) {
                errorFunction()
            }

            override fun onDismiss() {
                toast("dismiss")
            }

        }
        checkDialog.show(supportFragmentManager, null)

    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> true
    }*/
}