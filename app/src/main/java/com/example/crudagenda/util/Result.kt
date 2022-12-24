package com.example.crudagenda.util


sealed class ResultData<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultData<T>(data)
    class Error<T>(message: String?) : ResultData<T>(message = message)
    class ErrorNetwork<T>(message: String?) : ResultData<T>(message = message)
    class Loading<T> : ResultData<T>()
    class NoData<T> : ResultData<T>()
}
