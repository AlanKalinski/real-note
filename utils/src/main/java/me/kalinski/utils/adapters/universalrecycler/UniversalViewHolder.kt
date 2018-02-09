package me.kalinski.utils.adapters.universalrecycler

import android.support.v7.widget.RecyclerView
import android.view.View
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject

abstract class UniversalViewHolder<T: BaseItemObject>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindObject(obj: T, action: RowItemClick<T>?)
}