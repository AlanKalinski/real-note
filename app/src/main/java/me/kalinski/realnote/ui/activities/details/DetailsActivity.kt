package me.kalinski.realnote.ui.activities.details

import android.os.Bundle
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


    }
}