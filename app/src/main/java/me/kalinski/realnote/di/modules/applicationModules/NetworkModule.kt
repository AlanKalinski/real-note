package me.kalinski.realnote.di.modules.applicationModules

import android.app.Application
import com.kamsoft.zap.di.interceptor.AppInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import me.kalinski.realnote.di.interceptor.AuthInterceptor
import me.kalinski.realnote.di.modules.applicationModules.holder.ServerAddressHolder
import me.kalinski.realnote.di.modules.applicationModules.qualifier.Client
import me.kalinski.realnote.di.scope.ApplicationScope
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.*

@Module
class NetworkModule {

    @Provides
    fun provideCacheFile(app: Application): File {
        val cacheFile = File(app.cacheDir, UUID.randomUUID().toString())
        cacheFile.mkdir()
        return cacheFile
    }

    @Provides
    fun provideCache(file: File) = Cache(file, 10 * 1024 * 1024)

    @Provides
    fun provideMoshi() = Moshi.Builder()
            .build()

    @ApplicationScope
    @Client
    @Provides
    fun provideOsozClient(
            loggingInterceptor: HttpLoggingInterceptor,
            auth: AuthInterceptor,
            app: AppInterceptor,
            cache: Cache
    ) = OkHttpClient.Builder()
            .addInterceptor(auth)
            .addInterceptor(app)
            .addNetworkInterceptor(loggingInterceptor)
            .cache(cache)
            .build()

    @ApplicationScope
    @Provides
    fun provideRetrofit(
            @Client okHttpClient: OkHttpClient,
            moshi: Moshi,
            url: ServerAddressHolder
    ) = Retrofit.Builder()
            .baseUrl(url.string)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}

