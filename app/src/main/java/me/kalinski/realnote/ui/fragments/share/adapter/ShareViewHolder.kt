package me.kalinski.realnote.ui.fragments.share.adapter

import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.widget.TextView
import me.kalinski.realnote.R
import me.kalinski.utils.adapters.universalrecycler.UniversalViewHolder
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import org.jetbrains.anko.find

class ShareViewHolder(val view: View) : UniversalViewHolder<ShareUserWrapper>(view) {

    val displayName by lazy { view.find<TextView>(R.id.displayName) }
    val checkbox by lazy { view.find<AppCompatImageView>(R.id.checkbox) }

    override fun bindObject(obj: ShareUserWrapper, action: RowItemClick<ShareUserWrapper>?) {
        displayName.text = if (obj.displayName.isBlank()) obj.email else obj.displayName
        view.setOnClickListener {
            obj.checked = !obj.checked
            checkbox.visibility = if (obj.checked) View.VISIBLE else View.INVISIBLE
            action?.onClickItem(obj)
        }
    }
}