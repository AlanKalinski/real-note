package me.kalinski.realnote.storage.data

interface NotesServiceApi {

    interface NotesServiceCallback<in T> {

        fun onLoaded(notes: T)
    }

    fun getAllNotes(callback: NotesServiceCallback<List<Note>>)

    fun getNote(noteId: String, callback: NotesServiceCallback<Note>)

    fun saveNote(note: Note)
}
