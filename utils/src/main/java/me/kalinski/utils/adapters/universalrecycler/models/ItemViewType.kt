package me.kalinski.utils.adapters.universalrecycler.models

interface ItemViewType<out T: Int> {
    val viewType: T
}