package me.kalinski.realnote.di.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import me.kalinski.realnote.R
import org.jetbrains.anko.find

const val DEFAULT_GROUP_ID: Int = 1337

abstract class BaseToolbar(
        var _toolbarTitle: Any = "",
        var _showToolbar: Boolean = true,
        var _showBackarrow: Boolean = true,
        var _toolbarSubtitle: Any? = null,
        var _toolbarColor: Int = R.color.primary,
        var _statusbarColor: Int = R.color.primary_dark
) : AppCompatActivity() {

    protected var menu: Menu? = null

    val titleView by lazy { find<TextView>(R.id.actionbar_title) }
    val subtitleView by lazy { find<TextView>(R.id.actionbar_subtitle) }
    val logoView by lazy { find<ImageView>(R.id.actionbar_logo) }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun setupToolbar(
            title: Any,
            visible: Boolean = true,
            toolbarId: Int = R.id.toolbar
    ) {
        findViewById<Toolbar>(toolbarId)?.let {
            if (supportActionBar == null) {
                setSupportActionBar(it)
            }

            buildBase(title, visible = visible)
        }
    }

    private fun buildBase(
            title: Any,
            visible: Boolean = true
    ) {
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setToolbarTitle(title)
            _toolbarSubtitle?.let { setToolbarSubtitle(it) }

            if (visible) show() else hide()
        }
    }

    fun setToolbarTitle(title: Any) {
        _toolbarTitle = title
        titleView.text = when (title) {
            is Int -> {
                val txt = resources.getText(title)
                setTitle(txt)
                txt
            }
            is String -> {
                setTitle(title)
                title
            }
            else -> {
                setTitle("")
                ""
            }
        }
    }

    fun setToolbarSubtitle(subtitle: Any) {
        _toolbarSubtitle = subtitle

        subtitleView.apply {
            text = when (subtitle) {
                is Int -> resources.getText(subtitle)
                is String -> subtitle
                else -> ""
            }
            visibility = if (!text.isNullOrBlank()) View.VISIBLE
            else View.GONE
        }
    }

    fun setLeftAction(
            leftIconRes: Int? = null
    ) {
        val resizeDrawable = leftIconRes?.let {
            val drawable: Drawable = resources.getDrawable(leftIconRes)
            val bitmapDrawable = drawable as BitmapDrawable
            BitmapDrawable(
                    resources,
                    Bitmap.createScaledBitmap(
                            bitmapDrawable.bitmap,
                            resources.getDimensionPixelSize(R.dimen.menu_actionbar_icon_size),
                            resources.getDimensionPixelSize(R.dimen.menu_actionbar_icon_size),
                            true
                    )
            )
        }
        supportActionBar?.apply {
            if (resizeDrawable != null) setHomeAsUpIndicator(resizeDrawable)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }


    fun showLeftIcon() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun hideLeftIcon() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    fun setRightAction(
            menu: Menu,
            rightIconId: Int,
            rightIconRes: Int,
            rightIconTitle: Int,
            layoutResID: Int? = null
    ) {
        menu.removeItem(rightIconId)
        val menuItem = menu.add(
                DEFAULT_GROUP_ID,
                rightIconId,
                menu.size(),
                getString((rightIconTitle))
        )

        menuItem.apply {
            setIcon(rightIconRes)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            if (layoutResID != null) setActionView(layoutResID)
        }
    }

    fun showRightAction(
            menu: Menu,
            rightIconId: Int
    ) {
        menu.findItem(rightIconId).isVisible = true
    }

    fun hideRightAction(
            menu: Menu,
            rightIconId: Int
    ) {
        menu.findItem(rightIconId).isVisible = false
    }

    fun removeRightAction(
            menu: Menu,
            rightIconId: Int
    ) {
        menu.removeItem(rightIconId)
    }

    fun initialize() {
        initStatusbarColor(_statusbarColor)
        initToolbarColor(_toolbarColor)
        if (_showToolbar) setupToolbar(title = _toolbarTitle).run { if (_showBackarrow) setActionBarArrowEnable(true) }
        invalidateOptionsMenu()
    }

    fun initStatusbarColor(@ColorRes color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(color)
    }

    private fun initToolbarColor(@ColorRes color: Int) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(color)))
    }

    protected fun hideActionBar() {
        supportActionBar?.hide()
    }

    fun setActionBarArrowEnable(enabled: Boolean) {
        if (enabled) {
            setLeftAction()
            showLeftIcon()
        } else hideLeftIcon()
    }
}