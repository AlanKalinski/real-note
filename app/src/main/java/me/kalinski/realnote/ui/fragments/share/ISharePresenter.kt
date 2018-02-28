package me.kalinski.realnote.ui.fragments.share

import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.ui.fragments.share.adapter.ShareUserWrapper

interface ISharePresenter : BaseMvp.MvpPresenter<IShareView> {
    fun loadUsers()
    fun shareNote(noteToShare: Note, usersToShare: List<ShareUserWrapper>)
}