package me.kalinski.realnote.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.scope.ApplicationScope
import me.kalinski.realnote.storage.SharedPrefs

@Module
class ZapModule {

    @ApplicationScope
    @Provides
    fun provideSharedPreferences(app: Application) = SharedPrefs(app)

}

