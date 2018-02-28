package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.activities.utils.DisplayableProcessor
import me.kalinski.realnote.di.scope.ApplicationScope

@Module
class DisplayableModule {

    @ApplicationScope
    @Provides
    fun provideDisplayableProcessor() = DisplayableProcessor()
}