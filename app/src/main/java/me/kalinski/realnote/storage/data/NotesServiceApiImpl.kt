package me.kalinski.realnote.storage.data

import android.os.Handler
import java.util.*
import javax.inject.Inject

/**
 * Implementation of the Notes Service API that adds a latency simulating network.
 */
class NotesServiceApiImpl @Inject constructor() : NotesServiceApi {
    override fun getAllNotes(callback: NotesServiceApi.NotesServiceCallback<List<Note>>) {
        // Simulate network by delaying the execution.
        val handler = Handler()
        handler.postDelayed({
            val notes = ArrayList(NOTES_SERVICE_DATA.values)
            callback.onLoaded(notes)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getNote(noteId: String, callback: NotesServiceApi.NotesServiceCallback<Note>) {
        val handler = Handler()
        handler.postDelayed({
            val note = NOTES_SERVICE_DATA[noteId]
            note?.let { callback.onLoaded(note) }
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun saveNote(note: Note, callback: NotesServiceApi.NotesServiceCallback<Note>) {
        NOTES_SERVICE_DATA.put(note.id, note)
        callback.onLoaded(note)
    }

    companion object {
        private val SERVICE_LATENCY_IN_MILLIS = 2000
        private val NOTES_SERVICE_DATA = NotesServiceApiEndpoint.loadPersistedNotes()
    }

}
