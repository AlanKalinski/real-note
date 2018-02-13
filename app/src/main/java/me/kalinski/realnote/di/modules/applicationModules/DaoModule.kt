package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.scope.ApplicationScope
import me.kalinski.realnote.storage.daos.NotesDAO
import me.kalinski.realnote.storage.daos.UserDAO

@Module(includes = arrayOf(
        ServiceModule::class
))
class DaoModule {

    @ApplicationScope
    @Provides
    fun provideUserDAO() = UserDAO()

    @ApplicationScope
    @Provides
    fun provideNoteDAO(userDAO: UserDAO) = NotesDAO(userDAO)

}