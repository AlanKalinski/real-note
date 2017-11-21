package me.kalinski.realnote.ui.activities.details

import me.kalinski.realnote.di.base.BaseMvp

interface IDetailsPresenter : BaseMvp.MvpPresenter<DetailsView> {
    fun loadNoteDetails(id: String)
}