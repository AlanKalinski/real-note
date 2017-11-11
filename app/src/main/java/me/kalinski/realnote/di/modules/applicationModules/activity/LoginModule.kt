package me.kalinski.realnote.di.modules.applicationModules.activity

import dagger.Binds
import dagger.Module
import me.kalinski.realnote.ui.activities.login.ILoginInteractor
import me.kalinski.realnote.ui.activities.login.ILoginPresenter
import me.kalinski.realnote.ui.activities.login.LoginInteractor
import me.kalinski.realnote.ui.activities.login.LoginPresenter

@Module
abstract class LoginModule {

    @Binds
    abstract fun provideLoginPresenter(presenter: LoginPresenter): ILoginPresenter

    @Binds
    abstract fun provideLoginInteractor(dataManager: LoginInteractor): ILoginInteractor

}