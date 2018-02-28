package me.kalinski.realnote.ui.fragments.share

import io.reactivex.Observable
import io.reactivex.Single
import me.kalinski.realnote.storage.models.Note
import me.kalinski.realnote.ui.fragments.share.adapter.ShareUserWrapper

interface IShareInteractor {
    fun loadWrappedUsers(): Single<List<ShareUserWrapper>>
    fun shareNote(noteToShare: Note, usersToShare: List<ShareUserWrapper>): Observable<Boolean>
}