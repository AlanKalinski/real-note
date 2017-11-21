package me.kalinski.realnote.ui.activities.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.kalinski.realnote.R
import me.kalinski.realnote.storage.data.Note
import me.kalinski.utils.adapters.universalrecycler.UniversalAdapter
import me.kalinski.utils.adapters.universalrecycler.UniversalViewHolder
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick

class NotesListAdapter(action: RowItemClick<Note>?) : UniversalAdapter<Note>(action) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UniversalViewHolder<Note> =
            NotesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.row_note, parent, false))
}