package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.scope.ApplicationScope
import me.kalinski.realnote.storage.data.NotesServiceApi
import me.kalinski.realnote.storage.data.NotesServiceApiImpl

@Module(includes = arrayOf(
        NetworkModule::class
))
class ServiceModule {

    /*@ApplicationScope
    @Provides
    fun provideClinicChatService(retrofit: Retrofit) = retrofit.create(VisimedService.ClinicChatService::class.java)*/

    @ApplicationScope
    @Provides
    fun provideNotesService(): NotesServiceApi = NotesServiceApiImpl()


}
