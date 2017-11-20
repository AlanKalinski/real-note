package me.kalinski.realnote.ui.activities.addnote

import android.os.Bundle
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.ui.activities.details.DetailsPresenter
import me.kalinski.realnote.ui.activities.details.DetailsView
import javax.inject.Inject

class AddNoteActivity : BaseActivity(), AddNoteView {

    @Inject
    lateinit var presenter: AddNotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)


    }
}