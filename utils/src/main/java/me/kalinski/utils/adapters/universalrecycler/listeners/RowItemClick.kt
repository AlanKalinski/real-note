package me.kalinski.utils.adapters.universalrecycler.listeners

import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject

interface RowItemClick<in T: BaseItemObject> {
    fun onClickItem(item: T)
}