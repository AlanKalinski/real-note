package me.kalinski.realnote.ui.fragments.share.adapter

import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject

data class ShareUserWrapper(
        val displayName: String,
        val email: String,
        var checked: Boolean = false
) : BaseItemObject {
    override fun differenceItem(): Any? = email

    override fun viewType(): Int = 1
}