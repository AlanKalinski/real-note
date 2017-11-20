package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.modules.applicationModules.holder.ServerAddressHolder
import me.kalinski.realnote.di.modules.applicationModules.qualifier.DebugMode
import me.kalinski.realnote.di.modules.applicationModules.qualifier.MainUrl
import me.kalinski.realnote.di.scope.ApplicationScope

@Module
class InfoModule {

    @ApplicationScope
    @DebugMode
    @Provides
    fun provideDebugMode(): Boolean {
        return true
    }

    @ApplicationScope
    @MainUrl
    @Provides
    fun provideMainUrl(): String {
        return ""
    }

    @ApplicationScope
    @Provides
    fun provideMainUrlHolder(@MainUrl url: String): ServerAddressHolder {
        return ServerAddressHolder(url)
    }

}

