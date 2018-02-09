package me.kalinski.realnote.ui.activities.main

import io.reactivex.Single
import me.kalinski.realnote.storage.data.Note

interface IMainInteractor {
    fun loadNotes(): Single<List<Note>>
}