package me.kalinski.realnote.ui.activities.main.adapter

import android.view.View
import kotlinx.android.synthetic.main.row_note.view.*
import me.kalinski.realnote.storage.data.Note
import me.kalinski.utils.adapters.universalrecycler.UniversalViewHolder
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import me.kalinski.utils.extensions.fromHtml

class NotesViewHolder(view: View) : UniversalViewHolder<Note>(view) {
    override fun bindObject(obj: Note, action: RowItemClick<Note>?) {
        itemView.noteTitle.text = obj.title.fromHtml()
        itemView.noteDescription.text = obj.description.fromHtml()

        itemView.setOnClickListener { action?.onClickItem(obj) }
    }

    override fun bindObject(obj: Note, prevObj: Note?, futuObj: Note?, action: RowItemClick<Note>?) {
        bindObject(obj, action)
    }
}