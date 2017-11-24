package me.kalinski.realnote.ui.activities.addnote

import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_addnote.*
import me.kalinski.realnote.R
import me.kalinski.realnote.R.layout.activity_addnote
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.ui.activities.details.DetailsPresenter
import me.kalinski.realnote.ui.activities.details.DetailsView
import javax.inject.Inject

class AddNoteActivity : BaseActivity(), AddNoteView {

    @Inject
    lateinit var presenter: AddNotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_addnote)
        showKeyboard()

        editNote.apply {
            this.setPadding(8, 8, 8, 8)
            this.setPlaceholder(getString(R.string.insert_note_here_text))
            this.setEditorHeight(200)
            this.setEditorBackgroundColor(ContextCompat.getColor(context, R.color.appBackground))
        }
    }
}