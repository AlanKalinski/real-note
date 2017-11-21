package me.kalinski.realnote.storage.data

import android.support.annotation.VisibleForTesting


/**
 * Concrete implementation to load notes from the a data source.
 */
class InMemoryNotesRepository(private val mNotesServiceApi: NotesServiceApi) : NotesRepository {

    /**
     * This method has reduced visibility for testing and is only visible to tests in the same
     * package.
     */
    @VisibleForTesting
    private var mCachedNotes: List<Note>? = null

    override fun getNotes(callback: NotesRepository.LoadNotesCallback) {
        mCachedNotes?.let {
            callback.onNotesLoaded(it)
        } ?: run {
            mNotesServiceApi.getAllNotes(object : NotesServiceApi.NotesServiceCallback<List<Note>> {
                override fun onLoaded(notes: List<Note>) {
                    mCachedNotes = notes
                    callback.onNotesLoaded(mCachedNotes!!)
                }
            })
        }
    }

    override fun saveNote(note: Note) {
        mNotesServiceApi.saveNote(note)
        refreshData()
    }

    override fun getNote(noteId: String, callback: NotesRepository.GetNoteCallback) {
        mNotesServiceApi.getNote(noteId, object : NotesServiceApi.NotesServiceCallback<Note> {
            override fun onLoaded(note: Note) {
                callback.onNoteLoaded(note)
            }
        })
    }

    override fun refreshData() {
        mCachedNotes = null
    }

}
