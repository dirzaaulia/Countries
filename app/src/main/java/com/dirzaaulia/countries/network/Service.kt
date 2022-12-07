package com.dirzaaulia.countries.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dirzaaulia.countries.data.model.Country
import com.dirzaaulia.countries.data.model.State
import com.dirzaaulia.countries.utils.ServiceConstant
import com.dirzaaulia.countries.utils.provideAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

  @GET("countries")
  suspend fun getCountries(): Response<List<Country>>

  @GET("countries/{iso2}")
  suspend fun getCountryDetailWithISO2(
    @Path("iso2") iso2: String
  ): Response<Country>

  @GET("countries/{iso2}/states")
  suspend fun getStatesFromCountry(
    @Path("iso2") iso2: String
  ): Response<List<State>>

  companion object {
    fun create(context: Context): Service {
      val logger =
        HttpLoggingInterceptor().apply {
          level = HttpLoggingInterceptor.Level.BODY
        }

      val chuckerLogger = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(chuckerLogger)
        .addNetworkInterceptor(NetworkInterceptor())
        .retryOnConnectionFailure(false)
        .build()

      val moshi = provideAdapter()

      return Retrofit.Builder()
        .baseUrl(ServiceConstant.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(Service::class.java)
    }
  }
}