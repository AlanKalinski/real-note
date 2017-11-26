package me.kalinski.realnote.ui.activities.details

import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_details.*
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.ui.activities.main.MainActivity
import me.kalinski.utils.extensions.fromHtml
import me.kalinski.utils.extensions.getNavigationId
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenter

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        presenter.attachView(this)

        toolbar = setupToolbar(
                toolbarId = R.id.toolbar,
                visible = true,
                backArrowEnabled = true,
                title = ""
        )

        initNoteFromExtras()
        presenter.loadNoteDetails(getNavigationId())
    }

    private fun initNoteFromExtras() {
        val note = intent.getParcelableExtra<Note>(MainActivity.NOTE_INTENT_CONSTANT)
        showNoteDetails(note)
    }

    override fun showNoteDetails(note: Note) {
        toolbar?.title = note.title.fromHtml()
        noteDescription.text = note.description.fromHtml()
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }
}