package me.kalinski.realnote.di.activities.utils

import me.kalinski.realnote.di.activities.BaseFragment

interface Displayable {

    fun getDisplayItem(): BaseFragment
    fun getTagName(): String
    fun canShow(): Boolean

}