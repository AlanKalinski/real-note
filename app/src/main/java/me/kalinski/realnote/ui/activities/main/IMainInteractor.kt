package me.kalinski.realnote.ui.activities.main

import io.reactivex.Completable
import io.reactivex.Single
import me.kalinski.realnote.storage.data.Note

interface IMainInteractor {
    fun loadNotes(): Single<List<Note>>
    fun saveSampleNotes(): Completable
}