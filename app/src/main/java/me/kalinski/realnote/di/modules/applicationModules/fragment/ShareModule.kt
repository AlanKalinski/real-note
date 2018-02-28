package me.kalinski.realnote.di.modules.applicationModules.fragment

import dagger.Binds
import dagger.Module
import me.kalinski.realnote.ui.fragments.share.IShareInteractor
import me.kalinski.realnote.ui.fragments.share.ISharePresenter
import me.kalinski.realnote.ui.fragments.share.ShareInteractor
import me.kalinski.realnote.ui.fragments.share.SharePresenter

@Module
abstract class ShareModule {
    @Binds
    abstract fun provideSharePresenter(presenter: SharePresenter): ISharePresenter

    @Binds
    abstract fun provideShareInteractor(dataManager: ShareInteractor): IShareInteractor

}