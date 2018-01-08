package me.kalinski.realnote.storage.data

import android.support.annotation.VisibleForTesting


/**
 * Concrete implementation to load Notes from the a data source.
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
            callback.onNotesLoaded(it.sortedByDescending { it.createDate })
        } ?: run {
            mNotesServiceApi.getAllNotes(object : NotesServiceApi.NotesServiceCallback<List<Note>> {
                override fun onLoaded(notes: List<Note>) {
                    mCachedNotes = notes
                    callback.onNotesLoaded(mCachedNotes!!.sortedByDescending { it.createDate })
                }
            })
        }
    }

    override fun saveNote(note: Note, callback: NotesRepository.SaveNoteCallback) {
        mNotesServiceApi.saveNote(note, object : NotesServiceApi.NotesServiceCallback<Note> {
            override fun onLoaded(notes: Note) {
                callback.onNoteSaved(notes)
            }
        })
        refreshData()
    }

    override fun getNote(noteId: String, callback: NotesRepository.GetNoteCallback) {
        mNotesServiceApi.getNote(noteId, object : NotesServiceApi.NotesServiceCallback<Note> {
            override fun onLoaded(notes: Note) {
                callback.onNoteLoaded(notes)
            }
        })
    }

    override fun refreshData() {
        mCachedNotes = null
    }

}
