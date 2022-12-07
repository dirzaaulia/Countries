package com.dirzaaulia.countries.network

import com.dirzaaulia.countries.utils.ServiceConstant.API_KEY
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val modified = original.newBuilder()

        modified.addHeader("accept", "application/json")
        modified.addHeader("X-CSCAPI-KEY", API_KEY)

        modified.method(original.method, original.body)

        val request: Request = modified.build()
        return chain.proceed(request)
    }
}