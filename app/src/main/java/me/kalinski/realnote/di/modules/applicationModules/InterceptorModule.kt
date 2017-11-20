package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.interceptor.AuthInterceptor
import me.kalinski.realnote.di.scope.ApplicationScope

@Module
class InterceptorModule {

    @ApplicationScope
    @Provides
    fun provideAuthInterceptor() = AuthInterceptor()

}
