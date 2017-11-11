package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kalinski.realnote.di.modules.applicationModules.activity.LoginModule
import me.kalinski.realnote.di.scope.ActivityScope
import me.kalinski.realnote.ui.activities.login.LoginActivity

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    @ActivityScope
    abstract fun bindLoginActivity(): LoginActivity
}