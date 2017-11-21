package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.scope.ApplicationScope
import me.kalinski.realnote.storage.data.NoteRepositories
import me.kalinski.realnote.storage.data.NotesServiceApi

@Module(includes = arrayOf(
        ServiceModule::class
))
class DaoModule {
    /*@ApplicationScope
    @Provides
    fun provideUserDatabase(
            app: Application
    ) = Room.databaseBuilder(app, UserDatabase::class.java, "users")
            .allowMainThreadQueries()
            .build()

    @ApplicationScope
    @Provides
    fun provideUserDao(db: UserDatabase) = db.userDao()*/

    @ApplicationScope
    @Provides
    fun provideNoteDAO(notesServiceApi: NotesServiceApi) = NoteRepositories.getInMemoryRepoInstance(notesServiceApi)

}