package me.kalinski.realnote.di.modules.applicationModules.activity

import dagger.Binds
import dagger.Module
import me.kalinski.realnote.ui.activities.addnote.AddNoteInteractor
import me.kalinski.realnote.ui.activities.addnote.AddNotePresenter
import me.kalinski.realnote.ui.activities.addnote.IAddNoteInteractor
import me.kalinski.realnote.ui.activities.addnote.IAddNotePresenter

@Module
abstract class AddNoteModule {

    @Binds
    abstract fun provideAddNotePresenter(presenter: AddNotePresenter): IAddNotePresenter

    @Binds
    abstract fun provideAddNoteInteractor(dataManager: AddNoteInteractor): IAddNoteInteractor

}