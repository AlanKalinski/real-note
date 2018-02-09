package me.kalinski.utils.adapters.universalrecycler

import android.support.v7.widget.RecyclerView
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject
import kotlin.properties.Delegates

abstract class UniversalAdapter<O : BaseItemObject>(
        val action: RowItemClick<O>?
) : RecyclerView.Adapter<UniversalViewHolder<O>>(), AutoUpdatableAdapter {

    var itemList: List<O> by Delegates.observable(emptyList()) {
        _, oldValue, newValue ->
        autoNotify(oldValue, newValue) {
            o, n -> o.differenceItem() == n.differenceItem()
        }
    }

    override fun onBindViewHolder(holder: UniversalViewHolder<O>, position: Int) {
        holder.bindObject(itemList[position], action)
    }
    override fun getItemCount() = itemList.size
    override fun getItemViewType(position: Int) = itemList[position].viewType()

}