package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kalinski.realnote.di.modules.applicationModules.activity.AddNoteModule
import me.kalinski.realnote.di.modules.applicationModules.activity.DetailsModule
import me.kalinski.realnote.di.modules.applicationModules.activity.LoginModule
import me.kalinski.realnote.di.modules.applicationModules.activity.MainModule
import me.kalinski.realnote.di.scope.ActivityScope
import me.kalinski.realnote.ui.activities.addnote.AddNoteActivity
import me.kalinski.realnote.ui.activities.details.DetailsActivity
import me.kalinski.realnote.ui.activities.login.LoginActivity
import me.kalinski.realnote.ui.activities.main.MainActivity

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    @ActivityScope
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(DetailsModule::class))
    @ActivityScope
    abstract fun bindDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector(modules = arrayOf(AddNoteModule::class))
    @ActivityScope
    abstract fun bindAddNoteActivity(): AddNoteActivity
}