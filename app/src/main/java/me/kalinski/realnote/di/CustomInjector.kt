package me.kalinski.realnote.di

import android.app.Activity
import android.app.Service
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector

class CustomInjector {
    companion object {
        fun inject(activity: Activity, injector: HasActivityInjector) {
            injector.activityInjector().inject(activity)
        }

        fun inject(service: Service, injector: HasServiceInjector) {
            injector.serviceInjector().inject(service)
        }
    }
}