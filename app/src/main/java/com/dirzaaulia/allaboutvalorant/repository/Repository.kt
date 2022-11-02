package com.dirzaaulia.allaboutvalorant.repository

import androidx.annotation.WorkerThread
import com.dirzaaulia.allaboutvalorant.network.Service
import com.dirzaaulia.allaboutvalorant.utils.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(
  private val service: Service
) {

  @WorkerThread
  fun getAgents() = flow {
    emit(ResponseResult.Loading)
    try {
      val response = service.getAgents()
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