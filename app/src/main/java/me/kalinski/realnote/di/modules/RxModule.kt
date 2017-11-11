package me.kalinski.realnote.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class RxModule {
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}