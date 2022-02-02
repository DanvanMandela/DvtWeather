package com.example.dvtweather.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor constructor(
    private val key: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("appid", key).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}