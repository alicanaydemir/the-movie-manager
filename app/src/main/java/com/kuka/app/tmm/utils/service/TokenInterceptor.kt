package com.kuka.app.tmm.utils.service

import android.content.Context
import com.kuka.app.tmm.BuildConfig
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.utils.SharedHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val sharedHelper: SharedHelper, val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val modifiedRequest: Request =
            request.newBuilder()
                .header("Authorization", "Bearer " + BuildConfig.API_KEY)
                .header("Content-Type", "application/json")
                .build()

        return chain.proceed(modifiedRequest)
    }
}