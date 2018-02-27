package me.kalinski.realnote.ui.activities.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_note.*
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.ui.activities.addnote.AddNoteActivity
import me.kalinski.realnote.ui.activities.details.DetailsActivity
import me.kalinski.realnote.ui.activities.main.adapter.NotesListAdapter
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import me.kalinski.utils.extensions.navigate
import me.kalinski.utils.extensions.navigateForResult
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    companion object {
        const val NOTE_INTENT_CONSTANT = "NOTE"
        const val REQUEST_ADD_NOTE = 1337
    }

    @Inject
    lateinit var presenter: MainPresenter

    val noteList by lazy {
        notesRecycler.layoutManager = layoutManager
        notesRecycler.adapter = listAdapter
        notesRecycler
    }

    val layoutManager by lazy {
        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }

    val listAdapter by lazy {
        NotesListAdapter(onRowClickLIstener())
    }

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = setupToolbar(
                toolbarId = R.id.toolbar,
                visible = true,
                backArrowEnabled = false,
                title = getString(R.string.main_activity_title)
        )

        initViewComponents()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        requestForNotes()
    }

    private fun requestForNotes() {
        presenter.loadNotes()
    }

    private fun initViewComponents() {
        btnAdd.setOnClickListener {
            navigateForResult<AddNoteActivity>(requestCode = REQUEST_ADD_NOTE, sharedElements = Pair.create(btnAdd, btnAdd.transitionName))
        }
        swipeRefresh.setOnRefreshListener { requestForNotes() }
        noteList
    }

    override fun showNotes(notes: List<Note>) {
        Timber.d("Notes loaded: %s", notes.toString())
        layoutManager.smoothScrollToPosition(notesRecycler, null, 0)
        listAdapter.itemList = notes.sortedByDescending { it.editDate }
    }

    override fun showProgress() {
        Timber.d("Refreshing start")
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        Timber.d("Refreshing finished")
        swipeRefresh.isRefreshing = false
    }

    private fun onRowClickLIstener() = object : RowItemClick<Note> {
        override fun onClickItem(item: Note) {
            val intent = Intent()
            intent.putExtra(NOTE_INTENT_CONSTANT, item)

            navigate<DetailsActivity>(
                    item.uid,
                    intent,
                    Pair.create(noteTitle, noteTitle.transitionName),
                    Pair.create(noteDescription, noteDescription.transitionName)
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                requestForNotes()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}