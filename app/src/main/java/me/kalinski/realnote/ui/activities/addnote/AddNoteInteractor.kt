package me.kalinski.realnote.ui.activities.addnote

import io.reactivex.Flowable
import me.kalinski.realnote.storage.daos.NotesDAO
import me.kalinski.realnote.storage.models.Note
import javax.inject.Inject

class AddNoteInteractor @Inject constructor(
        val notesDAO: NotesDAO
) : IAddNoteInteractor {
    override fun saveNote(note: Note) = notesDAO.insertOrUpdate(Flowable.just(note))
}