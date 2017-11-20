package me.kalinski.realnote.di.interceptor

import okhttp3.Interceptor
import javax.inject.Inject


class DeviceInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val authRequest = originalRequest.newBuilder()

        return chain.proceed(authRequest.build())
    }
}