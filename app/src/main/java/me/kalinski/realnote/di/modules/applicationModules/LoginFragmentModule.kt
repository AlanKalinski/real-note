package me.kalinski.realnote.di.modules.applicationModules

import dagger.Binds
import dagger.Module
import me.kalinski.realnote.ui.fragments.login.ILoginPresenter
import me.kalinski.realnote.ui.fragments.login.LoginPresenter

@Module
abstract class LoginFragmentModule {
    @Binds
    abstract fun provideLoginPresenter(presenter: LoginPresenter): ILoginPresenter
}