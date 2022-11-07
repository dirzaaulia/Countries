package com.dirzaaulia.countries.repository

import androidx.annotation.WorkerThread
import com.dirzaaulia.countries.network.Service
import com.dirzaaulia.countries.utils.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(
  private val service: Service
) {
  @WorkerThread
  fun getCountries() = flow {
    emit(ResponseResult.Loading)
    try {
      val response = service.getCountries()
      response.body()?.let {
        emit(ResponseResult.Success(it))
      } ?: run {
        throw HttpException(response)
      }
    } catch (throwable: Throwable) {
      emit(ResponseResult.Error(throwable))
    }
  }.flowOn(Dispatchers.IO)

  @WorkerThread
  fun getCountryDetailWithISO2(iso2: String) = flow {
    emit(ResponseResult.Loading)
    delay(2500)
    try {
      val response = service.getCountryDetailWithISO2(iso2 = iso2)
      response.body()?.let {
        emit(ResponseResult.Success(it))
      } ?: run {
        throw HttpException(response)
      }
    } catch (throwable: Throwable) {
      emit(ResponseResult.Error(throwable))
    }
  }.flowOn(Dispatchers.IO)
}