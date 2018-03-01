package me.kalinski.realnote.di.modules.applicationModules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kalinski.realnote.di.modules.applicationModules.fragment.ShareModule
import me.kalinski.realnote.di.scope.FragmentScope
import me.kalinski.realnote.ui.fragments.login.LoginFragment
import me.kalinski.realnote.ui.fragments.share.ShareFragment

@Module
internal abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(
            modules = arrayOf(
                    ShareModule::class)
    )
    abstract fun bindNotificationCenterFragment(): ShareFragment

    @FragmentScope
    @ContributesAndroidInjector(
            modules = arrayOf(
                    LoginFragmentModule::class)
    )
    abstract fun bindLoginFragment(): LoginFragment

}