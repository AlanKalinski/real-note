package me.kalinski.realnote.di.binding

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kalinski.realnote.di.scope.ServiceScope
import me.kalinski.realnote.services.firebase.FirebaseIdService

@Module
abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    @ServiceScope
    abstract fun bindFirebaseIdService(): FirebaseIdService

}
