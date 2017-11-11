package me.kalinski.realnote.config

import android.app.Activity
import android.app.Application
import android.app.Service
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import me.kalinski.realnote.di.components.AppComponent
import me.kalinski.realnote.di.components.DaggerAppComponent
import javax.inject.Inject

class RealNoteApp : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return serviceInjector
    }
}