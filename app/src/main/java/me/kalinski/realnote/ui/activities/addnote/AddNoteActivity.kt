package me.kalinski.realnote.ui.activities.addnote

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_addnote.*
import me.kalinski.realnote.R
import me.kalinski.realnote.R.layout.activity_addnote
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.utility.RichEditorUtils
import me.kalinski.utils.extensions.children
import timber.log.Timber
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
            this.setEditorBackgroundColor(ContextCompat.getColor(context, R.color.appBackground))
            this.setEditorFontSize(14)
            this.setEditorFontColor(ContextCompat.getColor(context, R.color.primary_text))
        }

        setOnToolboxClick(findViewById(R.id.textToolbox))
    }

    private fun setOnToolboxClick(viewGroup: ViewGroup?) {
        val buttonGroup = viewGroup?.findViewById<LinearLayout>(R.id.buttonGroup)
        buttonGroup?.let {
            it.children.forEach { it.setOnClickListener { view -> RichEditorUtils.toolboxAction(it, editNote) } }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Timber.d("Note saved: %s", editNote.html)
    }

}