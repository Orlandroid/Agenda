package com.example.crudagenda.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend inline fun <T> ViewModel.safeDbOperation(
    result: MutableLiveData<ResultData<T>>,
    crossinline dbOperation: suspend () -> Unit,
) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            withContext(Dispatchers.Main) {
                result.value = ResultData.Loading()
            }
            dbOperation()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                result.value = ResultData.Error(e.message)
            }
        }
    }
}