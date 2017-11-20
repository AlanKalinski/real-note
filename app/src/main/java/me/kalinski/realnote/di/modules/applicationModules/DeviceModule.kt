package me.kalinski.realnote.di.modules.applicationModules

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.modules.applicationModules.qualifier.AppVersion
import me.kalinski.realnote.di.modules.applicationModules.qualifier.DeviceHeight
import me.kalinski.realnote.di.modules.applicationModules.qualifier.DeviceWidth
import me.kalinski.realnote.di.scope.ApplicationScope

@Module
class DeviceModule {

    @ApplicationScope
    @Provides
    fun provideDeviceDisplay(app: Application): Display =
            (app.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

    @ApplicationScope
    @AppVersion
    @Provides
    fun provideVersion(app: Application): String =
            app.packageManager.getPackageInfo(app.packageName, 0).versionName

    @ApplicationScope
    @DeviceWidth
    @Provides
    fun provideDeviceWidth(display: Display): Int {
        val point = Point()
        display.getSize(point)
        return point.x
    }

    @ApplicationScope
    @DeviceHeight
    @Provides
    fun provideDeviceHeight(display: Display): Int {
        val point = Point()
        display.getSize(point)
        return point.y
    }

}


