package com.dirzaaulia.allaboutvalorant.utils

import com.dirzaaulia.allaboutvalorant.utils.ResponseResult.Error
import com.dirzaaulia.allaboutvalorant.utils.ResponseResult.Success

/**
 * A generic class that holds a value or an exception
 */
sealed class ResponseResult<out R> {
  data class Success<out T>(val data: T) : ResponseResult<T>()
  data class Error(val throwable: Throwable) : ResponseResult<Nothing>()
  object Loading: ResponseResult<Nothing>()
}

inline fun <T> executeWithResponse(body: () -> T): ResponseResult<T> {
  return try {
    Success(body.invoke())
  } catch (throwable: Throwable) {
    throwable.printStackTrace()
    Error(throwable)
  }
}