package com.itunessearchassesment.middelware.utils

import com.itunessearchassesment.BuildConfig
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpUtils {
    val loggingInterceptor: Interceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            return loggingInterceptor
        }

    fun getHttpClient(
        authenticator: Authenticator?,
        vararg interceptors: Interceptor?
    ): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.callTimeout(40, TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(40, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(40, TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(40, TimeUnit.SECONDS)

        okHttpBuilder.followRedirects(false)
        okHttpBuilder.followSslRedirects(false)
        okHttpBuilder.retryOnConnectionFailure(false)

        for (interceptor in interceptors) {
            okHttpBuilder.addInterceptor(interceptor!!)
        }
        if (authenticator != null) {
            okHttpBuilder.authenticator(authenticator)
        }
        return okHttpBuilder.build()
    }

}