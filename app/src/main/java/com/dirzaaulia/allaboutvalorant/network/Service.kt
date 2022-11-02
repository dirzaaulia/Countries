package com.dirzaaulia.allaboutvalorant.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dirzaaulia.allaboutvalorant.data.response.AgentResponse
import com.dirzaaulia.allaboutvalorant.utils.ServiceConstant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

  @GET("agents")
  suspend fun getAgents(
    @Query("isPlayableCharacter") isPlayableCharacter: Boolean = true
  ): Response<AgentResponse>

  companion object {
    fun create(context: Context): Service {
      val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

      val chuckerLogger = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(chuckerLogger)
        .retryOnConnectionFailure(false)
        .build()

      val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

      return Retrofit.Builder()
        .baseUrl(ServiceConstant.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(Service::class.java)
    }
  }
}