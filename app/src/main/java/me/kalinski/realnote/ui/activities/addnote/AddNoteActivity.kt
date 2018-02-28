package me.kalinski.realnote.ui.activities.addnote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_addnote.*
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.utility.RichEditorUtils
import me.kalinski.utils.extensions.children
import me.kalinski.utils.extensions.toast
import javax.inject.Inject

class AddNoteActivity : BaseActivity(_showToolbar = false), AddNoteView {

    companion object {
        const val ADDED_NOTE: String = "ADDED_NOTE"
    }

    @Inject
    lateinit var presenter: IAddNotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)
        showKeyboard()

        presenter.attachView(this)

        editNote.apply {
            this.setPadding(8, 8, 8, 8)
            this.setPlaceholder(getString(R.string.insert_note_here_text))
            this.setEditorBackgroundColor(ContextCompat.getColor(context, R.color.appBackground))
            this.setEditorFontSize(14)
            this.setEditorFontColor(ContextCompat.getColor(context, R.color.primary_text))
        }

        setOnToolboxClick(findViewById(R.id.textToolbox))
        btnSave.setOnClickListener { presenter.saveNote(noteTitle.text.toString(), editNote.html) }
    }

    private fun setOnToolboxClick(viewGroup: ViewGroup?) {
        val buttonGroup = viewGroup?.findViewById<LinearLayout>(R.id.buttonGroup)
        buttonGroup?.let {
            it.children.forEach { it.setOnClickListener { view -> RichEditorUtils.toolboxAction(it, editNote) } }
        }
    }

    override fun onNoteSaved() {
        toast(getString(R.string.note_saved_message))
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}