package me.kalinski.realnote.storage.data

object NoteRepositories {

    private var repository: NotesRepository? = null

    @Synchronized
    fun getInMemoryRepoInstance(notesServiceApi: NotesServiceApi): NotesRepository {
        if (null == repository) {
            repository = InMemoryNotesRepository(notesServiceApi)
        }
        return repository as NotesRepository
    }
}