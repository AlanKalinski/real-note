package me.kalinski.realnote.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_note.*
import me.kalinski.realnote.R
import me.kalinski.realnote.R.layout.activity_main
import me.kalinski.realnote.di.activities.BaseActivity
import me.kalinski.realnote.storage.data.Note
import me.kalinski.realnote.ui.activities.addnote.AddNoteActivity
import me.kalinski.realnote.ui.activities.details.DetailsActivity
import me.kalinski.realnote.ui.activities.main.adapter.NotesListAdapter
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import me.kalinski.utils.extensions.navigate
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    companion object {
        val NOTE_INTENT_CONSTANT = "NOTE"
    }

    @Inject
    lateinit var presenter: MainPresenter

    val noteList by lazy {
        notesRecycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        notesRecycler.adapter = listAdapter
        notesRecycler
    }

    val listAdapter by lazy {
        NotesListAdapter(onRowClickLIstener())
    }

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        presenter.attachView(this)

        toolbar = setupToolbar(
                toolbarId = R.id.toolbar,
                visible = true,
                backArrowEnabled = true,
                title = getString(R.string.main_activity_title)
        )

        initViewComponents()
        requestForNotes()
    }

    private fun requestForNotes() {
        presenter.loadNotes()
    }

    private fun initViewComponents() {
        btnAdd.setOnClickListener {
            navigate<AddNoteActivity>(sharedElements = Pair.create(btnAdd, btnAdd.transitionName))
        }
        noteList
    }

    override fun showNotes(notes: List<Note>) {
        listAdapter.itemList = notes.toMutableList()
    }

    private fun onRowClickLIstener() = object : RowItemClick<Note> {
        override fun onClickItem(item: Note) {
            val intent = Intent()
            intent.putExtra(NOTE_INTENT_CONSTANT, item)

            navigate<DetailsActivity>(
                    item.id,
                    intent,
                    Pair.create(noteTitle, noteTitle.transitionName),
                    Pair.create(noteDescription, noteDescription.transitionName)
            )
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}