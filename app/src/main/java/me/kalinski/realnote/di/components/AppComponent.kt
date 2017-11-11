package me.kalinski.realnote.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import me.kalinski.realnote.config.RealNoteApp
import me.kalinski.realnote.di.binding.ServiceBindingModule
import me.kalinski.realnote.di.interceptor.AuthInterceptor
import me.kalinski.realnote.di.modules.RxModule
import me.kalinski.realnote.di.modules.ZapModule
import me.kalinski.realnote.di.modules.applicationModules.*
import me.kalinski.realnote.di.scope.ApplicationScope

@Component(modules = arrayOf(
        ZapModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ServiceBindingModule::class,
        ServiceModule::class,
        DaoModule::class,
        InterceptorModule::class,
        RxModule::class,
        DeviceModule::class,
        InfoModule::class
))
@ApplicationScope
interface AppComponent : AndroidInjector<DaggerApplication> {

    val application: Application
    fun authInterceptor(): AuthInterceptor

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(app: Application): Builder
    }

    fun inject(app: RealNoteApp)
}