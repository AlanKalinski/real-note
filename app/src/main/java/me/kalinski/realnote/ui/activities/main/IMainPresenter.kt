package me.kalinski.realnote.ui.activities.main

import me.kalinski.realnote.di.base.BaseMvp

interface IMainPresenter : BaseMvp.MvpPresenter<MainView>{
    fun loadNotes()
}