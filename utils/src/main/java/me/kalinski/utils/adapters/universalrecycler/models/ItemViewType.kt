package me.kalinski.utils.adapters.universalrecycler.models

interface ItemViewType<out T: Any> {
    fun viewType() : T
}