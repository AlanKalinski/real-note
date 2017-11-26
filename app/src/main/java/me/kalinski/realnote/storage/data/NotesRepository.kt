package me.kalinski.realnote.storage.data

interface NotesRepository {

    interface LoadNotesCallback {
        fun onNotesLoaded(notes: List<Note>)
    }

    interface GetNoteCallback {
        fun onNoteLoaded(note: Note)
    }

    interface SaveNoteCallback {
        fun onNoteSaved(note: Note)
    }

    fun getNotes(callback: LoadNotesCallback)

    fun getNote(noteId: String, callback: GetNoteCallback)

    fun saveNote(note: Note, callback: SaveNoteCallback)

    fun refreshData()
}
