package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module

@Module(includes = arrayOf(
        NetworkModule::class
))
class ServiceModule {

    /*@ApplicationScope
    @Provides
    fun provideClinicChatService(retrofit: Retrofit) = retrofit.create(VisimedService.ClinicChatService::class.java)*/

}
