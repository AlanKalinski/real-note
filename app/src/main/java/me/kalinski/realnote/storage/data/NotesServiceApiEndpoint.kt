package me.kalinski.realnote.storage.data

import android.support.v4.util.ArrayMap

object NotesServiceApiEndpoint {

    private val DATA: ArrayMap<String, Note> = ArrayMap(2)

    init {
        addNote("First in memory note!", " Donec vel mollis nunc, accumsan viverra enim. Duis laoreet leo sit amet turpis vehicula, a cursus eros ultricies. Sed ut condimentum diam. Donec a dapibus orci, id dignissim mauris. Sed pharetra eros quis risus accumsan dictum. Vivamus varius, dolor nec suscipit mattis, felis arcu facilisis eros, vel fermentum est metus vitae sem. Aliquam erat volutpat. Vivamus consectetur nulla lectus, at efficitur metus suscipit sit amet. Nam vel viverra tellus, et maximus lectus. ")
        addNote("Lorem ipsum", " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin commodo vestibulum lectus id blandit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas vitae varius erat. Nullam vestibulum rhoncus ipsum ut pharetra. Maecenas eu eros id sem rutrum rhoncus pretium non ligula. Suspendisse congue venenatis odio, vel vulputate lorem pellentesque tempus. Etiam porttitor neque diam, sed lacinia elit facilisis quis. Cras cursus tristique leo, porttitor consequat sem auctor at. Vestibulum ac mattis sem. Cras non velit ut elit dictum placerat in non elit. Vestibulum eget dictum felis. Cras maximus massa non purus tempus, eget tristique diam ornare. Sed in fermentum elit. Nunc elementum enim justo, vel viverra ante malesuada id. In hac habitasse platea dictumst. ")
    }

    private fun addNote(title: String, description: String, imageUrl: String? = null) {
        val newNote = Note(title, description, imageUrl)
        DATA.put(newNote.id, newNote)
    }

    /**
     * @return the Notes to show when starting the app.
     */
    fun loadPersistedNotes(): ArrayMap<String, Note> {
        return DATA
    }
}
