package me.kalinski.realnote.ui.activities.main

import android.os.Bundle
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}