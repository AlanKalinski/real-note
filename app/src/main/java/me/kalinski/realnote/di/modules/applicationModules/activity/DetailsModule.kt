package me.kalinski.realnote.di.modules.applicationModules.activity

import dagger.Binds
import dagger.Module
import me.kalinski.realnote.ui.activities.details.DetailsInteractor
import me.kalinski.realnote.ui.activities.details.DetailsPresenter
import me.kalinski.realnote.ui.activities.details.IDetailsInteractor
import me.kalinski.realnote.ui.activities.details.IDetailsPresenter

@Module
abstract class DetailsModule {

    @Binds
    abstract fun provideDetailsPresenter(presenter: DetailsPresenter): IDetailsPresenter

    @Binds
    abstract fun provideDetailsInteractor(dataManager: DetailsInteractor): IDetailsInteractor

}