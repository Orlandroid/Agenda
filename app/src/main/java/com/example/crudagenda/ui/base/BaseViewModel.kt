package com.example.crudagenda.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crudagenda.util.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {
    private var job: Job? = null

    suspend inline fun <T> safeDbOperation(
        result: MutableLiveData<ResultData<T>>,
        crossinline dbOperation: suspend () -> Unit,
    ) {
        try {
            withContext(Dispatchers.Main) {
                result.value = ResultData.Loading()
            }
            withContext(Dispatchers.IO) {
                dbOperation()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.w("Error", e.message.toString())
                result.value = ResultData.Error(e.message)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}



