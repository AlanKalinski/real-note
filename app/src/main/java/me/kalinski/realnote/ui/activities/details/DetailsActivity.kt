package me.kalinski.realnote.ui.activities.details

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*
import me.kalinski.realnote.R
import me.kalinski.realnote.R.layout.activity_details
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.ui.activities.main.MainActivity
import me.kalinski.utils.extensions.getNavigationId
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_details)
        presenter.attachView(this)

        initNoteFromExtras()
        presenter.loadNoteDetails(getNavigationId())
    }

    private fun initNoteFromExtras() {
        val note = intent.getParcelableExtra<Note>(MainActivity.NOTE_INTENT_CONSTANT)
        showNoteDetails(note)
    }

    override fun showNoteDetails(note: Note) {
        noteTitle.text = note.title
        noteDescription.text = note.description
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }
}