package com.kamsoft.zap.di.interceptor

import android.os.Build
import okhttp3.Interceptor
import javax.inject.Inject

class AppInterceptor @Inject constructor(
        val appVersion: String,
        val width: Int,
        val height: Int,
        val debugMode: Boolean
) : Interceptor {

    val TYPE = "Content-Type"
    val APP_VER = "APP_VER"
    val APP_NAME: String = "APP_NAME"
    val APP_LOGIN: String = "APP_LOGIN"
    val APP_PASS: String = "APP_PASS"
    val APP_TYPE: String = "APP_TYPE"
    val APP_PHONE: String = "APP_PHONE"
    val ADD_EXECUTE_TIME = "ADD_EXECUTE_TIME"
    val APP_WIDTH = "APP_WIDTH"
    val APP_HEIGHT = "APP_HEIGHT"
    val APP_SYS = "APP_SYS"
    val APP_SYS_VER = "APP_SYS_VER"

    override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val appIntercept = originalRequest.newBuilder()
                .addHeader(APP_VER, appVersion)
                .addHeader(TYPE, "application/json")
                .addHeader(APP_NAME, "MobilnaWizytaPacjenta")
                .addHeader(APP_TYPE, "MOBILNA_WIZYTA_PACJENTA")
                .addHeader(APP_LOGIN, "MobilnaWizytaPacjenta")
                .addHeader(APP_PASS, "P@ssw0rd")
                .addHeader(APP_WIDTH, width.toString())
                .addHeader(APP_HEIGHT, height.toString())
                .addHeader(APP_PHONE, android.os.Build.MODEL)
                .addHeader(APP_SYS, "ANDROID")
                .addHeader(APP_SYS_VER, Build.VERSION.RELEASE)

        if (debugMode) appIntercept.addHeader(ADD_EXECUTE_TIME, "7")

        return chain.proceed(appIntercept.build())
    }
}