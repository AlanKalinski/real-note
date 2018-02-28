package me.kalinski.realnote.ui.activities.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_details.*
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.ui.activities.main.MainActivity
import me.kalinski.realnote.ui.fragments.share.ShareFragment
import me.kalinski.utils.extensions.fromHtml
import me.kalinski.utils.extensions.getNavigationId
import javax.inject.Inject

const val SHARE_MENU_ID = 9

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenter

    val note: Note by lazy { intent.getParcelableExtra<Note>(MainActivity.NOTE_INTENT_CONSTANT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        presenter.attachView(this)

        initNoteFromExtras()
        presenter.loadNoteDetails(getNavigationId())
    }

    private fun initNoteFromExtras() {
        showNoteDetails(note)
    }

    override fun showNoteDetails(note: Note) {
        setToolbarTitle(note.title.fromHtml().toString())
        noteDescription.text = note.description.fromHtml()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setRightAction(menu, SHARE_MENU_ID, R.drawable.ic_share, R.string.share)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            SHARE_MENU_ID -> {
                shareButtonAction()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun shareButtonAction() {
        displayableProcessor.addToQueue(ShareFragment()).run { displayableProcessor.show() }
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }
}