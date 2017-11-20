package me.kalinski.realnote.di.modules.applicationModules.activity

import dagger.Binds
import dagger.Module
import me.kalinski.realnote.ui.activities.main.IMainInteractor
import me.kalinski.realnote.ui.activities.main.IMainPresenter
import me.kalinski.realnote.ui.activities.main.MainInteractor
import me.kalinski.realnote.ui.activities.main.MainPresenter

@Module
abstract class MainModule {

    @Binds
    abstract fun provideMainPresenter(presenter: MainPresenter): IMainPresenter

    @Binds
    abstract fun provideMainInteractor(dataManager: MainInteractor): IMainInteractor

}