package me.kalinski.realnote.ui.fragments.share.adapter

import android.view.ViewGroup
import me.kalinski.realnote.R
import me.kalinski.utils.adapters.universalrecycler.UniversalAdapter
import me.kalinski.utils.adapters.universalrecycler.UniversalViewHolder
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import me.kalinski.utils.extensions.inflate

class ShareListAdapter(action: RowItemClick<ShareUserWrapper>) : UniversalAdapter<ShareUserWrapper>(action) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversalViewHolder<ShareUserWrapper> =
            ShareViewHolder(parent.inflate(R.layout.row_share))
}